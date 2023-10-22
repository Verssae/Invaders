# Currency System


## Team Introduction

### Team's Goal
Establishing an effective virtual economy system to engage and sustain the interest of game users over time.

### Team's Vision
Currency becomes the Key Attraction Point that affects the influx of new users and continued use of the game.

### Roles of Each Team Member

| Name | Item    | Requirements                                  |
|------|---------|-----------------------------------------------|
| Songeun Lee | Enhancement Currency | Weapon Enhancement(midterm) - area and attack, Tower Addition and Enhancement(final) |
| Hayeon Choi | Buy Skins | Change Ship Color(midterm), Change Character and Background Image |
| Daeun Song | Stage Rewards | Random Rewards, Rewards Based on Game Outcome(Stage Clear) |
| Siwoo Ryu | Item Store | Store UI, Item(bomb, shild, enhanced Stone) Purchase System |
| Seoyeon Park | Monster Kill Rewards | add Coin Entity, Coin Rewards for Enemy Defeat |  
| Minseo Kim | Ship Regeneration | Health Points Restoration(midterm), Device-Specific Currency Differences, Variation in Currency Based on Recovery Amount | 


## Detail Requirements

### 1. Enhancement Currency
- Enhance Weapon (Spaceship) | Increase weapon damage (attack, area) using enhanced Stone.
    - Weapon Damage Increase 1: Instead of firing one shot, you can penetrate through enemies in a straight line n times. (midterm)
    - Weapon Damage Increase 2: Instead of firing one shot, you can eliminate all enemies within a certain radius of a single target.
    - Weapon Damage Increase 3: Increase the weapon's damage per shot. (midterm) 
- Add and Upgrade Towers | Use enhanced stone to add towers (up to a maximum of 3) and enhance specific towers (basic, 1st-tier, 2nd-tier).
[Team 6 Item System / Team 2 Sound Effects] 

### 2. Buy Skins 
- Skin Shop Configuration: Character, Background (Team 6 Item System)
- Add character color and pattern (Team 7 Gameplay HUD)
- Make Background Images Changeable (Team 7 Gameplay HUD)
- Click the purchase button → coins decrease → available status → skin change when selected (releaseable)

### 3. Stage Rewards
- Random Rewards: After clearing a stage, allow the player to choose one out of three buttons to receive a random coin reward.
- Reward Based on Game Outcome: Grant coins based on the score obtained or the time taken when clearing a stage.

### 4. Item Store
- Item Store UI Layout - Placing buttons and inserting 2D images
- Integration with the Main Screen - Creating a button to navigate to the store
- Establishing a Currency System - Currency used for skins and enhancement systems 
- Displaying Owned Currency on the Screen - Coins, Enhancement Stones

### 5. Monster Kill Rewards
- Raise the value of your coins every time you kill an enemy.
    - Raise the value more for special enemies such as bosses
      
### 6. Ship Regeneration
- Aircraft(or ship) HP Recovery System
    - If the game has ended and the user is willing to pay an additional cost, it allows the recovery of an aircraft with the same performance as the user's.
    - Number of recoveries: 1 (Can be chosen depending on the money available / based on the stage difficulty)
    - The higher the performance of the aircraft, the higher the recovery cost.
 
## Dependencies on Other Teams:
- Team 6 | Item System | Bomb, Shild, Coin and Enhanced Stone Drop
- Team 7 | HUD | Button UI for using BOMB and SHILD, Timer
- Team 5 | Player & Enemy Ship Variety | Skin Color Parameter
