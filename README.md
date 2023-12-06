## Monpoke

### Quickstart

`./gradlew build run`

> Run Sample Game: \
`./gradlew build runSample`


### Dependencies
- Java 8
- Gradle 7.3
- Junit 4

### Game Summary
- The game consists of 2 teams, each team has a variable number of Monpoké.
- Each Monpoké has 2 attributes, HitPoints (HP) and Attack Power (AP).
- The 2 teams engage in a simple turn-based battle.
- A team’s turn can be either choosing, heal, revive a friendly Monpoké or attack a Monpoké with their currently
chosen one.
- Attacking Monpoké depletes the enemy Monpoké HP for the value of their AP.
- Healing Monpoké restore HP of the selected Monpoké for the amount of heal given.
- Reviving a Monpoké restore all the HP of the Monpoké. (each team can use revive once per match)
- A Monpoké is defeated when its HP is less than or equal to 0.
- The game ends when all of a team’s Monpoké have been defeated.
We ask that you build a program that plays the game! The program should accept text
commands given over standard input. Each input command should echo the expected output to
standard out. The program should allow for:
    1. Creation of 2 teams and their respective Monpoké
    2. The battle begins and teams take turns back and forth
    3. A winner is determined
    
#### Commands
| Input                                               | Output                                                                                     |
|-----------------------------------------------------|--------------------------------------------------------------------------------------------|
| CREATE `<team-id>` `<monpoké-id>` `<hp>` `<attack>` | `<monpoké-id>` has been assigned to team `<team-id>`!                                      |
| ATTACK                                              | `<current-monpoké-id>` attacked `<enemy-monpoké-id>` for `<current-monpoké-id-AP>` damage! |
| ICHOOSEYOU `<monpoké-id>`                           | `<monpoké-id>` has entered the battle!                                                     |
| HEAL `<heal-amount>`                                | `<monpoké-id>` healed for `<heal-amount>` to `<new-health>`                                |
| REVIVE `<monpoké-id>`                               | `<monpoké-id>` has been revived                                                            |
| * when a monpoké is defeated                        | `<enemy-monpoké-id>` has been defeated!                                                    |
| * when all monpoké on a team are defeated           | `<team-id>` is the winner!                                                                 |

#### Rules
##### Team and Monpoké creation
- The first CREATE command for a Monpoké implicitly creates the team.
- Teams can create Monpoké in any order.
- Teams have multiple Monpoké
- The battle stage starts when the first ICHOOSEYOU command happens.
- Validation
    - Pokemon must have 1 HP or greater
    - Pokemon must have 1 AP or greater
    - The battle cannot begin until there are two teams
##### Battle
- The team that was created first takes the first turn
- A turn is either choosing, healing, reviving or attacking a Monpoké with the a chosen Monpoké
- The first turn of each team must be choosing a Monpoké
- Attacks do damage equal to the attack value to the currently chosen enemy Monpoké
- A Monpoké is considered defeated when it’s HP is 0 or lower
- Healing Monpoké restore HP of the selected Monpoké for the amount of heal given.
- Reviving a Monpoké restore all the HP of the Monpoké. (each team can use revive once per match)
- When a Monpoké is defeated the owning team’s next turn must be choosing a new
Monpoké
- Validation
    - A team cannot choose, heal or attack with a Monpoké
        - Who is not on your team
        - Who is not currently chosen
        - Who is defeated 
  - A team cannot revive a Monpoké
        - Who is not on your team
        - Whos is alive  

#### Game Ends
- When all of a team’s Monpoké are defeated

#### Input
- Commands are well-formed but might be against the rules. No need to handle special
characters or typos in the commands themselves
- In the event that a game violates the rules, the program should exit with exit code 1
- Commands are space-delimited and arguments are positional
- The game should be able to take input from a file or from standard in, ie
    - monpoke inputfile
    - monpoke < inputfile
- Output happens after all the commands are fed in
