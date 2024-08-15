export interface LoginDto {
    username: string,
    password: string
}

export interface RegisterDto {
    username: string,
    password: string,
    age: number,
    height: number,
    weight: number,
}

export enum Role {
    ADMIN = "ADMIN",
    USER = "USER"
}

export interface LoginUserDto {
    uid: number,
    username: string,
    age: number,
    height: number,
    weight: number,
    days: DayDto[],
    workoutPrograms: WorkoutProgramsDto[],
    role: Role,
}
export interface MealDto{
    mid: number,
    user:  Partial<LoginUserDto> | undefined,
    name: string,
    type: string,
    ingredients: PersonalizedIngredientDto[],
    calories: number,
    proteins: number,
    fats: number,
    carbs: number
}
export interface DayDto{
    did: number,
    date: string,
    meals: MealDto[],
    workouts: WorkoutDto[]
}
export interface WorkoutProgramsDto{
    wpid: number,
    name: string,
    workouts: WorkoutDto[]
}

export interface IngredientDto {
    iid: number,
    name: string,
    calories: number,
    protein: number,
    carbs: number,
    fats: number,
}
export interface PersonalizedIngredientDto {
    piid: number,
    ingredient: IngredientDto,
    quantity: number,
    calories: number,
    proteins: number,
    fats:number,
    carbs: number,
    selected: boolean
}
export interface ExerciseDto {
    eid: number,
    name: string,
    type: string,
}
export interface PersonalizedExerciseDto{
    peid: number,
    exercise: ExerciseDto,
    reps: number,
    sets: number,
    weight: number,
    time: number
    selected: boolean
}
export interface WorkoutDto {
    wid: number,
    name: string,
    duration: number,
    exercises: PersonalizedExerciseDto[]
}
export interface AddIngredientDialogProps{
    open: boolean,
    handleClose: () => void,
    handleSubmit: (ingredient: IngredientDto) => void,
    ingredient: IngredientDto,
    handleChange: (event: any) => void
}
export interface AddExerciseDialogProps {
    open: boolean,
    handleClose: () => void,
    handleSubmit: (exercise: ExerciseDto) => void,
    exercise: ExerciseDto
    handleChange : (event: any) => void
}
export interface AddMealDialogProps {
    open: boolean,
    handleClose: () => void,
    handleSubmit: (meal: MealDto, personalizedIngredients: Array<PersonalizedIngredientDto>) => void,
    handleChange:(event: any) => void,
    meal: MealDto,
    personalizedIngredients: Array<PersonalizedIngredientDto>
}
export interface AddWorkoutDialogProps{
    open: boolean,
    handleClose: () => void,
    handleSubmit: (wrokout: WorkoutDto, personalizedExercise: Array<PersonalizedExerciseDto>) => void,
    handleChange:(event: any) => void,
    workout: WorkoutDto,
    personalizedExercises: Array<PersonalizedExerciseDto>
}
