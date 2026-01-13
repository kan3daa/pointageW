# Pointage

Application JavaFX de pointage des bénévoles (code 4 chiffres), avec enregistrement dans une base SQLite.

## Cloner le projet
```bash
git clone https://github.com/kan3daa/pointageW.git
cd pointageW/
```

## Lancer le projet
```bash
mvn clean javafx:run
```

## Fonctionnalités
- [x] Écran de bienvenue.
- [x] Saisie du code bénévole.
- [x] Affichage “Bonjour Prénom Nom”.
- [x] Pointage matin / après‑midi + option repas.
- [x] Enregistrement des pointages dans SQLite.
- [ ] Bouton Administrateur avec Visualisation et Impression.

## Base de données
- SQLite (fichier `data/pointage.db`).
- Tables : `benevole`, `pointage`.
- Initialisation automatique au démarrage (création des tables + jeu d’essai).
