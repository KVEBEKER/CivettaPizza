package ru.kvebekshaev.civettapizza.domain.entities

enum class ProductCategory(val priority: Int) {
    Combo(0),
    Pizza(1),
    Drink(2),
    Snack(3),
    Desert(4)
}