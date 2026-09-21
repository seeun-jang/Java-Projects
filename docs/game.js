let balance = 0;
let velocity = 0;
let windForce = 0;

let leftPressed = false;
let rightPressed = false;

let gameOver = false;

let startTime = Date.now();
let score = 0;

let windCounter = 0;

const flamingo = document.getElementById("flamingo");
const marker = document.getElementById("marker");

const windText = document.getElementById("wind");
const scoreText = document.getElementById("score");

const gameOverScreen = document.getElementById("game-over");
const finalScore = document.getElementById("final-score");

document.addEventListener("keydown", (event) => {

    if (event.key.toLowerCase() === "a") {
        leftPressed = true;
    }

    if (event.key.toLowerCase() === "d") {
        rightPressed = true;
    }

    if (event.key.toLowerCase() === "r" && gameOver) {
        restartGame();
    }
});

document.addEventListener("keyup", (event) => {

    if (event.key.toLowerCase() === "a") {
        leftPressed = false;
    }

    if (event.key.toLowerCase() === "d") {
        rightPressed = false;
    }
});

function updateWind() {

    windCounter++;

    if (windCounter >= 40) {

        windForce = (Math.random() - 0.5) * 0.08;

        windCounter = 0;
    }
}

function getWindDirection() {

    if (windForce < -0.01) {
        return "LEFT";
    }

    if (windForce > 0.01) {
        return "RIGHT";
    }

    return "CALM";
}

function updateGame() {

    if (gameOver) {
        return;
    }

    updateWind();

    if (leftPressed) {
        velocity -= 0.20;
    }

    if (rightPressed) {
        velocity += 0.20;
    }

    velocity += windForce;

    velocity *= 0.98;

    balance += velocity;

    score = Math.floor(
        (Date.now() - startTime) / 1000
    );

    if (Math.abs(balance) >= 30) {

        gameOver = true;

        gameOverScreen.classList.remove("hidden");

        finalScore.textContent =
            "Survival Time: " + score + " sec";

        return;
    }

    updateScreen();
}

function updateScreen() {

    flamingo.style.transform =
        `translateX(-50%) rotate(${balance}deg)`;

    const markerPosition =
        Math.max(-200, Math.min(200, balance * 6));

    marker.style.left =
        `calc(50% + ${markerPosition}px)`;

    windText.textContent =
        "Wind: " + getWindDirection();

    scoreText.textContent =
        "Survival: " + score + " sec";
}

function restartGame() {

    balance = 0;
    velocity = 0;
    windForce = 0;

    leftPressed = false;
    rightPressed = false;

    gameOver = false;

    windCounter = 0;

    startTime = Date.now();

    gameOverScreen.classList.add("hidden");

    updateScreen();
}

function gameLoop() {

    updateGame();

    requestAnimationFrame(gameLoop);
}

updateScreen();
gameLoop();
