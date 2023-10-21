# [Clean Code Team](https://github.com/Clean-Code-Team/Invaders) (Team 1)

> ## Team Requirements
Visual Effect System
<br><br>

> ## Team Introduction
|No.| Name     | Role |
|---------|-------------------------|-----------|
|1. |[Jeongwoo Jang](https://github.com/jeongwoo903/jang_jeongwoo)|👑 Team Leader|
|2. |[Doyoon Kim](https://github.com/doyoon323/doyoon323.git)|🗓️ Project Management| 
|3. |[Hyeonjeong Kim](https://github.com/258xsw/258xsw)|🗣️ Meeting Management|
|4. |[Jinho Shin](https://github.com/NiceGuy1313/shinjinho)|📋 Document Management|
|5. |[Chaeheon Lee](https://github.com/highlees/highlees)|🧑🏻‍💻 Code Management|
|6. |[Minkyeong Kang](https://github.com/alicek0/alicek0)|💬 Communication Management|

<br>

> ## 💻 Development Contents

💡 Game Background Animation -> [Minkyeong Kang](https://github.com/alicek0/alicek0)
- Animated background lines with perspective
- Gradient background that gradually changes between colors
- Green glow behind player sprites
- Background becomes darker as player lives reaches 0 (from <=3)
- Background becomes yellower when special ship appears <br></br>

💡 Countdown Design -> [Doyoon Kim](https://github.com/doyoon323/doyoon323.git) 
 - Animated Loading box when game is started
 - Loading progress bar with gradient color (for 3 seconds)
 - The Loading string with blink effect gets bigger and smaller 
 - Change GO! string like neon sign <br><br>
 

💡 Ending Scene -> [Hyeonjeong Kim](https://github.com/258xsw/258xsw)
- Game Over & stage Clear pop-up
- Add moving ghost <br><br>

💡 Player & Enemy New Death FX -> [Jinho Shin](https://github.com/NiceGuy1313/shinjinho)
- Add enemy death effect such as skeletons, vortex etc. <br><br>

💡 Item Design for Item Team -> [Jinho Shin](https://github.com/NiceGuy1313/shinjinho)
- Add item Design such as enhancestone, buff item, ddbuff item. <br><br>

💡 Score Effects -> [Chaeheon Lee](https://github.com/highlees/highlees)
- Emoji generation based on game score changes
- Score color change effect according to game score change
- Write [test code](https://github.com/Clean-Code-Team/Invaders/blob/develop/test/scoreColorTest.java) and complete unit tests for ```scoreColor``` method<br><br>

💡 Flicker Effect -> [Chaeheon Lee](https://github.com/highlees/highlees)
- Create a flickering effect like a classic arcade screen<br>
(Applies to the Start screen and High Scores screen,<br>
other teams applied it to other screens as well.)
- Add an effect so that the Game Over screen gradually appears
- Write [test code](https://github.com/Clean-Code-Team/Invaders/blob/develop/test/slowlyChangingColorsTest.java) and complete unit tests for ```blinkingColor``` method <br>
and ```slowlyChangingColors``` method<br>
(The test code for the ```blinkingColor``` method can be found [here](https://github.com/Clean-Code-Team/Invaders/blob/develop/test/scoreColorTest.java).)
<br><br>

💡 Bullet Effects -> [Jeongwoo Jang](https://github.com/jeongwoo903/jang_jeongwoo)
- Change the graphic of a normal bullet
- Add a ```BulletY``` that flies at a specific interval
    - 1P - 2 normal bullets & 1 BulletY
    - 2P - 5 normal bullets & 1 BulletY (*Based on two players combined)
- Set BulletY's speed to be faster than regular bullets
- Set BulletY's color to yellow <br><br>

> ## Dependencies on Other Teams

- In 2P mode, we discussed with the [2Player Mode Team](https://github.com/Verssae/Invaders/blob/main/requirements/Joyroom.md) <br> to disable the feature that displays emojis based on scores. <br>
(Removed emoji effect to indicate remaining lives for 2 game players.)
<br></br>
