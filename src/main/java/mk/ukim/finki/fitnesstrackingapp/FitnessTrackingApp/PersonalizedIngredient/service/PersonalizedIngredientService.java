package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.service;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.PersonalizedIngredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.dto.PersonalizedIngredientDto;

import java.util.List;
import java.util.Optional;

public interface PersonalizedIngredientService {
    PersonalizedIngredient addPersonalizedIngredient(PersonalizedIngredient ingredient);
    Optional<PersonalizedIngredient> getById(Long id);

    void delete(Long id);

    List<PersonalizedIngredientDto> getAll();

    Long removeById(Long id);
}
