package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.Ingredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.IngredientsRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.dto.IngredientDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.PersonalizedIngredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.PersonalizedIngredientRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.service.PersonalizedIngredientService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientsRepository ingredientsRepository;
    private final PersonalizedIngredientRepository personalizedIngredientRepository;
    @Override
    public List<Ingredient> getAll() {
        return ingredientsRepository.findAll();
    }

    @Override
    @Transactional
    public void addIngredient(Ingredient ingredient) {
        ingredientsRepository.save(ingredient);
    }

    @Override
    public Ingredient getById(Long id) {
        return ingredientsRepository.findById(id).orElseThrow();
    }

    @Override
    public IngredientDto addIngredientFromDto(IngredientDto ingredient) {
        Ingredient ingredient1 = new Ingredient(
                ingredient.getName(),
                ingredient.getCalories(),
                ingredient.getProtein(),
                ingredient.getCarbs(),
                ingredient.getFats()
        );
        return ingredientsRepository.save(ingredient1).toDto();
    }

    @Override
    public List<Ingredient> getAllByCriteria(String searchCriteria) {
        return ingredientsRepository.findAllByNameLike(searchCriteria)
                .stream()
                .sorted(Comparator.comparing(Ingredient::getName))
                .toList();
    }

    @Override
    public Long removeById(Long id) {
        Ingredient ingredient = ingredientsRepository.findById(id).orElseThrow();
        for(PersonalizedIngredient personalizedIngredient : personalizedIngredientRepository.findAll()){
            if(personalizedIngredient.getIngredient().equals(ingredient)){
                personalizedIngredientRepository.delete(personalizedIngredient);
            }
        }
        ingredientsRepository.delete(ingredient);
        return ingredient.getIID();
    }

    @Override
    @Transactional
    public IngredientDto editIngredient(IngredientDto ingredient) {
        Ingredient ingredient1 = ingredientsRepository.findById(ingredient.getIid()).orElseThrow();
        ingredient1.setCalories(ingredient.getCalories());
        ingredient1.setProtein(ingredient.getProtein());
        ingredient1.setName(ingredient.getName());
        ingredient1.setCarbs(ingredient.getCarbs());
        ingredient1.setFats(ingredient.getFats());
        ingredientsRepository.save(ingredient1);

        return ingredient1.toDto();
    }
}
