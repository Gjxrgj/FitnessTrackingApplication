package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.dto.MealDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.dto.PersonalizedIngredientDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.PersonalizedIngredient;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meal")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "_meal_seq")
    @SequenceGenerator(name = "_meal_seq", sequenceName = "_meal_seq", allocationSize = 1)
    @Column(name = "mID")
    private Long mID;

    @JoinColumn(name = "uID")
    private Long uid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "mealhasingredient",
            joinColumns = @JoinColumn(name = "mID"),
            inverseJoinColumns = @JoinColumn(name = "piID")
    )
    private List<PersonalizedIngredient> ingredients;

    public Meal(String name, String type) {
        this.name = name;
        this.type = type;

        ingredients = new ArrayList<>();
    }
    public Meal(String name, Long uid, String type) {
        this.name = name;
        this.type = type;
        this.uid = uid;

        ingredients = new ArrayList<>();
    }
    public void addIngredient(PersonalizedIngredient ingredient){
        ingredients.add(ingredient);
    }
    public int getCalories(){
        int counter = 0;
        for(PersonalizedIngredient ingredient : this.ingredients){
            counter += ingredient.getCalories();
        }
        return counter;
    }
    public int getProteins(){
        int counter = 0;
        for(PersonalizedIngredient ingredient : this.ingredients){
            counter += ingredient.getProteins();
        }
        return counter;
    }
    public int getFats(){
        int counter = 0;
        for(PersonalizedIngredient ingredient : this.ingredients){
            counter += ingredient.getFats();
        }
        return counter;
    }
    public int getCarbs(){
        int counter = 0;
        for(PersonalizedIngredient ingredient : this.ingredients){
            counter += ingredient.getCarbs();
        }
        return counter;
    }
    public int getQuantity(){
        int counter = 0;
        for(PersonalizedIngredient ingredient : this.ingredients){
            counter += ingredient.getQuantity();
        }
        return counter;
    }
    public List<PersonalizedIngredientDto> getIngredientsAsDto(){
        List<PersonalizedIngredientDto> ingredientsDto = new ArrayList<>();
        for(PersonalizedIngredient ingredient : getIngredients()){
            ingredientsDto.add(new PersonalizedIngredientDto(
                    ingredient.getId(),
                    ingredient.getIngredient(),
                    ingredient.getQuantity(),
                    ingredient.getCalories(),
                    ingredient.getProteins(),
                    ingredient.getFats(),
                    ingredient.getCarbs()
            ));
        }
        return ingredientsDto;
    }

    public MealDto toDto() {
        return new MealDto(
                this.getMID(),
                this.getUid(),
                this.getName(),
                this.getType(),
                this.getIngredientsAsDto(),
                this.getCalories(),
                this.getProteins(),
                this.getFats(),
                this.getCarbs()
        );
    }
}
