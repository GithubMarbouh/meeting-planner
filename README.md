# Meeting Planner Application

Cette application permet aux collaborateurs de réserver des salles de réunion en fonction du type de réunion, du nombre de participants et de la disponibilité des salles.

## Types de réunion

- **Visioconférences (VC)** : Réunions avec des partenaires externes nécessitant un écran, une webcam et un appareil de visioconférence.
- **Séances de partage et d'études de cas (SPEC)** : Réunions nécessitant uniquement un tableau.
- **Réunions simples (RS)** : Réunions entre collègues sur site nécessitant une salle avec une capacité minimale de 3 personnes.
- **Réunions couplées (RC)** : Réunions entre collègues sur site et en télétravail nécessitant un tableau, un écran et un appareil de visioconférence.

## Contrôleurs

### RoomController

Ce contrôleur gère les opérations liées aux salles de réunion.

#### Endpoints

- `GET /api/v1/room` : Récupère la liste de toutes les salles disponibles.
- `GET /api/v1/room/{name}` : Récupère les détails d'une salle spécifique en fonction de son nom.
- `POST /api/v1/room/available/{meetingType}` : Récupère la liste des salles disponibles pour un type de réunion spécifique, en fonction des informations de réservation fournies dans le corps de la requête.
- `GET /api/v1/room/capacity/{capacity}` : Récupère la liste des salles avec une capacité minimale spécifiée.

### ReservationController

Ce contrôleur gère les opérations liées aux réservations de salles de réunion.

#### Endpoints

- `GET /api/v1/reservation/room/available` : Récupère la liste des salles disponibles en fonction des informations de réservation fournies dans le corps de la requête.
- `POST /api/v1/reservation` : Crée une nouvelle réservation de salle en fonction des informations fournies dans le corps de la requête.
- `GET /api/v1/reservation/meeting/{meetingType}` : Récupère la liste des réservations pour un type de réunion spécifique.
- `GET /api/v1/reservation/room/{roomName}` : Récupère la liste des réservations pour une salle spécifique.
- `GET /api/v1/reservation/today` : Récupère la liste des réservations pour la journée en cours.

## Exemples d'utilisation

- Récupérer la liste des salles disponibles pour une réunion simple de 4 personnes de 9h à 10h le 30 juin 2024 :
* POST /api/v1/reservation :
```json
{
  
  "startTime": "2024-06-30T09:00:00",
  "endTime": "2024-06-30T10:00:00",
  "meetingType": "RS",
  "participants": 4
}
```
