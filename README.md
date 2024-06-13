# subscriber_bc

## Description

Cette application est un microservice Spring Boot pour gérer les abonnés de Canal+. Elle utilise une base de données H2 pour stocker les données des abonnés et expose des endpoints REST pour créer, récupérer, mettre à jour et désactiver des abonnés.

## Composants Utilisés

### 1. Spring Boot

Spring Boot est utilisé pour simplifier la configuration et le déploiement de l'application.

### 2. Spring Data JPA

Spring Data JPA est utilisé pour interagir avec la base de données H2. Il permet d'effectuer des opérations CRUD sur les entités `Subscriber`.

### 3. H2 Database

H2 est une base de données en mémoire utilisée pour stocker les données des abonnés.

### 4. Bean Validation

Bean Validation est utilisé pour valider les données des DTO (Data Transfer Object) à l'aide d'annotations comme `@NotNull`.
pour assurer que toutes les données personnelles doivent être fournies au moment de la création de nouveaux abonnés moyennant de Group validation "CreateValidationGroup",

### 5. JUnit et Mockito

JUnit et Mockito sont utilisés pour écrire et exécuter des tests unitaires pour les différents composants de l'application.

## Structure du Projet

### 1. Model

Le package `com.example.subscriber_bc.model` contient la classe `Subscriber` qui représente un abonné.

```java
public class Subscriber {
    private Long subscriberId;
    private String fname;
    private String lname;
    private String mail;
    private String phone;
    private Boolean isActive;
}
````

### 2. Adapter

Le package com.example.subscriber_bc.adapter contient la classe SubscriberAdapter qui transforme les objets SubscriberDTO en Subscriber.

### 3. Repository

Le package com.example.subscriber_bc.repository contient l'interface SubscriberRepository qui étend JpaRepository pour fournir des opérations CRUD.

### 4. Service

Le package com.example.subscriber_bc.service contient la classe SubscriberService qui contient la logique métier pour gérer les abonnés.

### 5. Controller

Le package com.example.subscriber_bc.controller contient la classe SubscriberController qui expose les endpoints REST pour les opérations sur les abonnés.
    
     endPoints :
        - Créer un nouveau abonné: (nouvelle adresse mail et nouveau numéro)
            POST /subscribers
            {
            "fname": "il",
            "lname": "Bel",
            "mail": "il.b@gmail.com",
            "phone": "012354678"
            }
    
        - Récupérer des Abonnés (Renvoi tous les abonnés si pas de critères)
            GET /subscribers
            GET /subscribers?mail=il.b@gmail.com
            GET /subscribers?active=false&fname=Il&mail=il.b@gmail.com
        
        - Mettre à Jour les données personnelles d'un abonné (fname, lname, mail, phone):
            PUT /subscribers/{id}
            {
            "fname": "ilV2",
            "lname": "Bel",
            "mail": "il.bV2@gmail.com",
            "phone": "012354789"
            }

        - Désactiver un Abonné
            PUT /subscribers/deactivate/{id}

### 6. Exception Handling
Le package com.example.subscriber_bc.exception contient une classe globale de gestion des exceptions avec @ControllerAdvice.

### 7. Specification

Le package com.example.subscriber_bc.specification contient la classe SubscriberSpecification pour créer des spécifications dynamiques pour les requêtes de recherche.

### 8. Tests

Les tests sont écrits en utilisant JUnit et Mockito pour assurer la qualité et le bon fonctionnement des composants.



