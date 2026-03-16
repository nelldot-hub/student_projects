#include <iostream>
#include <cstdlib> // for rand() and srand()
#include <ctime>   // for time()

using namespace std;

int main()
{
    string playerChoice;
    int computerChoice;

    // Seed the random number generator
    srand(time(0));

    cout << "=== Rock, Paper, Scissors Game ===\n";
    cout << "Enter your choice (rock, paper, or scissors): ";
    cin >> playerChoice;

    // Convert player's input to lowercase for consistency
    for (auto &c : playerChoice)
        c = tolower(c);

    // Generate computer's choice: 0 = rock, 1 = paper, 2 = scissors
    computerChoice = rand() % 3;

    string computerMove;
    if (computerChoice == 0)
        computerMove = "rock";
    else if (computerChoice == 1)
        computerMove = "paper";
    else
        computerMove = "scissors";

    cout << "Computer chose: " << computerMove << endl;

    // Determine winner
    if (playerChoice == computerMove)
    {
        cout << "It's a tie!\n";
    }
    else if ((playerChoice == "rock" && computerMove == "scissors") ||
             (playerChoice == "paper" && computerMove == "rock") ||
             (playerChoice == "scissors" && computerMove == "paper"))
    {
        cout << "You win! 🎉\n";
    }
    else if (playerChoice == "rock" || playerChoice == "paper" || playerChoice == "scissors")
    {
        cout << "Computer wins! 😅\n";
    }
    else
    {
        cout << "Invalid input! Please type rock, paper, or scissors.\n";
    }

    return 0;
}