// ---------- Abstract class: shared identity + shared/abstract behavior ----------
abstract class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    // Identical for every animal -> lives here, written once.
    void sleep() {
        System.out.println(name + " is sleeping");
    }

    void eat() {
        System.out.println(name + " is eating");
    }

    // No shared implementation possible -> must be abstract.
    abstract void makeSound();
}

// ---------- Interfaces: optional, mixable abilities ----------
interface Swimmer {
    void swim();
}

interface Flyer {
    void fly();
}

// ---------- Concrete classes ----------
class Dog extends Animal implements Swimmer {
    Dog(String name) {
        super(name);
    }

    void makeSound() {
        System.out.println(name + " says Woof!");
    }

    public void swim() {
        System.out.println(name + " is swimming");
    }
}

class Cat extends Animal {
    Cat(String name) {
        super(name);
    }

    void makeSound() {
        System.out.println(name + " says Meow!");
    }
}

class Bird extends Animal implements Flyer {
    Bird(String name) {
        super(name);
    }

    void makeSound() {
        System.out.println(name + " says Tweet!");
    }

    public void fly() {
        System.out.println(name + " is flying");
    }
}

// ---------- Demo ----------
public class AnimalShelter {
    public static void main(String[] args) {
        Dog dog = new Dog("Buddy");
        Cat cat = new Cat("Whiskers");
        Bird bird = new Bird("Tweety");

        System.out.println("--- Dog ---");
        dog.eat();
        dog.makeSound();
        dog.sleep();
        dog.swim();

        System.out.println("\n--- Cat ---");
        cat.eat();
        cat.makeSound();
        cat.sleep();

        System.out.println("\n--- Bird ---");
        bird.eat();
        bird.makeSound();
        bird.sleep();
        bird.fly();
    }
}
