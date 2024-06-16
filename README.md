# Annuaire-CNAM

**L'annuaire du CNAM** est une application mobile développée par les auditeurs de la licence informatique en HTT dans le cadre du cours NFA025 : Mise en oeuvre programmation de smartphones et tablettes tactiles.

## Description

L'application **Annuaire CNAM** est conçue pour gérer et afficher les informations des classes, des élèves et des matières au sein du CNAM.

Elle permet de :

- **Lister les classes** : Affichage de toutes les classes disponibles.
- **Lister les élèves** : Affichage des élèves pour chaque classe.
- **Lister les matières** : Affichage des matières enseignées (ex : NFA025).
- **Gestion de CRUD** : Ajout, modification, suppression et consultation des informations pour chaque élément (classes, élèves, matières).

## Outils et versions

### Environnement de Développement

- **Android Studio** : Android Studio Koala | 2024.1.1

### Versions Utilisées

- **Base de données** : SQLite, version 1
- **API Android** : API 24 "Nougat", Android 7.0
- **JDK** : JDK 21
- **Version Java** : Java 21

### Dépendances et Outils Additionnels

- **Gradle** : Utilisé pour la gestion des dépendances et la compilation du projet.
- **Bibliothèques** :
    - **SQLite** : Pour la gestion de la base de données embarquée.
  
### Configuration de l'Environnement

1. **Installer Android Studio** :
    - Téléchargez et installez Android Studio Koala | 2024.1.1 depuis [le site officiel](https://developer.android.com/studio).
2. **Configurer le JDK** :
    - Assurez-vous que JDK 21 est installé et configuré dans Android Studio.
3. **Configurer le SDK Android** :
    - Assurez-vous que le SDK pour API 24 "Nougat" (Android 7.0) est installé.

## Compatibilité

### Versions d'Android

- **Minimum** : API 24 "Nougat", Android 7.0

### Tests de Compatibilité

- Des tests de compatibilité détaillés n'ont pas encore été effectués.
- Il est recommandé de tester l'application sur divers appareils et versions d'Android pour garantir une compatibilité optimale.

## Limitations

Certaines fonctionnalités peuvent ne pas fonctionner correctement sur les versions d'Android antérieures à Nougat (7.0).