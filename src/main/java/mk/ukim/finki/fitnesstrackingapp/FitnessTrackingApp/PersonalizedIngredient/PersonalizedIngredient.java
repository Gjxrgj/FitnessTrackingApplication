package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.Ingredient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "personalizedingredient")
public class PersonalizedIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "_personalized_ingredient_seq")
    @SequenceGenerator(name = "_personalized_ingredient_seq", sequenceName = "_personalized_ingredient_seq", allocationSize = 1)
    @Column(name = "piid")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "iid")
    private Ingredient ingredient;

    @Column(name = "quantity")
    private int quantity;

    public PersonalizedIngredient(Ingredient ingredient, int quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }
    public double getCoef(){
        return this.quantity/100.0;
    }
    public int getCalories(){
        return (int) (ingredient.getCalories() * getCoef());
    }
    public int getProteins(){
        return (int) (ingredient.getProtein() * getCoef());

    }
    public int getFats(){
        return (int) (ingredient.getFats() * getCoef());
    }
    public int getCarbs(){
        return (int) (ingredient.getCarbs() * getCoef());
    }

}
