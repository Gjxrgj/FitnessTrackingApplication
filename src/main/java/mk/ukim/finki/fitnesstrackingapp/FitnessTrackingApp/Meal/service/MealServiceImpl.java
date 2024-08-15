package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.Day;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.DayRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.Ingredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.Meal;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.dto.MealDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.PersonalizedIngredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.dto.PersonalizedIngredientDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.MealRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.service.IngredientService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.service.PersonalizedIngredientService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto.UserDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;

    private final IngredientService ingredientService;
    private final PersonalizedIngredientService personalizedIngredientService;
    private final UserService userService;
    private final HttpSession session;
    private final DayRepository dayRepository;
    @Override
    public List<MealDto> getAll() {
        List<Meal> meals = mealRepository.findAllByUidOrderByNameAsc(userService.getLoggedUser().getUID());
        return getMealTableDtos(meals);
    }

    private List<MealDto> getMealTableDtos(List<Meal> meals) {
        List<MealDto> mealsDto = new ArrayList<>();
        for(Meal meal : meals){
            mealsDto.add(new MealDto(
                    meal.getMID(),
                    userService.getLoggedUser().getUID(),
                    meal.getName(),
                    meal.getType(),
                    meal.getIngredientsAsDto(),
                    meal.getCalories(),
                    meal.getProteins(),
                    meal.getFats(),
                    meal.getCarbs()
                    ));
        }
        return mealsDto;
    }

    @Override
    @Transactional
    public void addMeal(String name, String type) {

     mealRepository.save(Meal.builder()
                     .uid(userService.getLoggedUser().getUID())
                     .name(name)
                     .type(type)
             .build());
    }
    @Override
    @Transactional
    public void AddIngredientToMeal(Long ingredientId, Long mealID, int quantity) {
        Ingredient ingredient = ingredientService.getById(ingredientId);
        Meal meal = mealRepository.findById(mealID).orElseThrow();
        PersonalizedIngredient personalizedIngredient = new PersonalizedIngredient();
        personalizedIngredient.setIngredient(ingredient);
        personalizedIngredient.setQuantity(quantity);
        personalizedIngredientService.addPersonalizedIngredient(personalizedIngredient);
        meal.addIngredient(personalizedIngredient);
        mealRepository.save(meal);
    }

    @Override
    public Meal getById(Long mealID) {
        return mealRepository.findById(mealID).orElseThrow();
    }

    @Override
    public List<PersonalizedIngredientDto> getPersonalizedIngredientsFromMeal(Long mealId) {
        Meal meal = mealRepository.findById(mealId).orElseThrow();
        List<PersonalizedIngredientDto> ingredientTableDtos = new ArrayList<>();
        for(PersonalizedIngredient ingredient : meal.getIngredients()){
            ingredientTableDtos.add(new PersonalizedIngredientDto(
                   ingredient.getId(),
                    ingredient.getIngredient(),
                    ingredient.getQuantity(),
                    ingredient.getCalories(),
                    ingredient.getProteins(),
                    ingredient.getFats(),
                    ingredient.getCarbs()
                    )
            );
        }
        return ingredientTableDtos;
    }

    @Override
    @Transactional
    public void removeMealFromDay(Long mealID, Long ingredientID) {
        PersonalizedIngredient ingredient = personalizedIngredientService.getById(ingredientID).orElseThrow();
        Meal meal = mealRepository.findById(mealID).orElseThrow();
        meal.getIngredients().remove(ingredient);
        personalizedIngredientService.delete(ingredientID);
        mealRepository.save(meal);
    }

    @Override
    public List<Meal> getAllByUserInSession() {
        User user = userService.getLoggedUser();
        List<Meal> meals = new ArrayList<>();
        for(Day day : user.getDays()){
            meals.addAll(day.getMeals());
        }
        return meals;
    }

    @Override
    public List<MealDto> getAllByUser() {
        User user = userService.getLoggedUser();
        List<Meal> meals =  mealRepository.findAllByUid(user.getUID());
        return getMealTableDtos(meals);
    }

    @Override
    @Transactional
    public void addNewMeal(MealDto mealDto) {

        Meal meal = new Meal(mealDto.getName(), mealDto.getUid(), mealDto.getType());

        List<PersonalizedIngredient> personalizedIngredients = new ArrayList<>();

        for (PersonalizedIngredientDto ingredientDto : mealDto.getIngredients()) {
            Ingredient ingredient = ingredientService.getById(ingredientDto.getIngredient().getIID());

            PersonalizedIngredient personalizedIngredient = new PersonalizedIngredient(
                    ingredient,
                    ingredientDto.getQuantity()
            );
            personalizedIngredientService.addPersonalizedIngredient(personalizedIngredient);
            personalizedIngredients.add(personalizedIngredient);
        }

        meal.setIngredients(personalizedIngredients);
        meal.setUid(userService.getLoggedUser().getUID());
        mealRepository.save(meal);
    }

    @Override
    public List<MealDto> getAllByUserIdAndCriteria(String searchCriteria) {
        UserDto user = userService.getUserData();
        List<Meal> meals =  mealRepository.findAllByUid(user.getUID())
                .stream()
                .filter(meal ->
                        meal.getName().toLowerCase()
                                .contains(searchCriteria.toLowerCase()))
                .sorted(Comparator.comparing(Meal::getName))
                .toList();
        return getMealTableDtos(meals);
    }

    @Override
    @Transactional
    public Long removeMeal(Long id) {
        Meal meal = mealRepository.findById(id).orElseThrow();
        for(PersonalizedIngredient ingredient : meal.getIngredients()){
            personalizedIngredientService.delete(ingredient.getId());
        }
        mealRepository.deleteById(id);
        return id;
    }

    @Override
    @Transactional
    public MealDto editMeal(MealDto mealDto) {
        Meal meal = mealRepository.findById(mealDto.getMID()).orElseThrow();
        meal.setUid(userService.getLoggedUser().getUID());
        meal.setName(mealDto.getName());
        meal.setType(mealDto.getType());
        List<PersonalizedIngredient> personalizedIngredients = new ArrayList<>();

        for(PersonalizedIngredientDto ingredient: mealDto.getIngredients()){
            personalizedIngredients.add(new PersonalizedIngredient(
                    ingredient.getPiid(),
                    ingredient.getIngredient(),
                    ingredient.getQuantity()
            ));
        }

        meal.setIngredients(personalizedIngredients);

        return mealRepository.save(meal).toDto();
    }

    @Override
    @Transactional
    public MealDto addMealToDay(MealDto meal) {
        User user = userService.getLoggedUser();
        Day day = user.getToday();
        day.addMealToDay(mealRepository.findById(meal.getMID()).orElseThrow());
        dayRepository.save(day);
        userService.save(user);
        return meal;
    }

}
