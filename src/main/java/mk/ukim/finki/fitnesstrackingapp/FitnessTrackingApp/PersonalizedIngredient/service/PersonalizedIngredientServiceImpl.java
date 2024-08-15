package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.Ingredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.service.IngredientService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.Meal;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.MealRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.PersonalizedIngredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.PersonalizedIngredientRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.dto.PersonalizedIngredientDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonalizedIngredientServiceImpl implements PersonalizedIngredientService {
    private final PersonalizedIngredientRepository personalizedIngredientRepository;
    private final IngredientService ingredientService;
    private final MealRepository mealRepository;
    private final UserService userService;
    @Override
    @Transactional
    public PersonalizedIngredient addPersonalizedIngredient(PersonalizedIngredient ingredient) {
        return personalizedIngredientRepository.save(ingredient);
    }

    @Override
    public Optional<PersonalizedIngredient> getById(Long id) {
        return personalizedIngredientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        PersonalizedIngredient ingredient = personalizedIngredientRepository.findById(id).orElseThrow();
        personalizedIngredientRepository.delete(ingredient);
    }

    @Override
    public List<PersonalizedIngredientDto> getAll() {
        List<PersonalizedIngredientDto> personalizedIngredients = new ArrayList<>();
        for(Ingredient ingredient: ingredientService.getAll()){
            personalizedIngredients.add(new PersonalizedIngredientDto(
                    ingredient.getIID(),
                    ingredient,
                    0,
                    ingredient.getCalories(),
                    ingredient.getProtein(),
                    ingredient.getFats(),
                    ingredient.getCarbs()
            ));
        }
        return personalizedIngredients;
    }

    @Override
    @Transactional
    public Long removeById(Long id) {
        PersonalizedIngredient personalizedIngredient = personalizedIngredientRepository.findById(id).orElseThrow();
        for(Meal meal : mealRepository.findAll()){
            if(meal.getIngredients().contains(personalizedIngredient)){
                meal.getIngredients().remove(personalizedIngredient);
                mealRepository.save(meal);
            }
        }
        personalizedIngredientRepository.delete(personalizedIngredient);
        return id;
    }


}
