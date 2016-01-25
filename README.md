# Calculate-163

Android app for the card game '163.' Players are given 6 playing cards to solve for 163 using arithmetic operators, similar to the game 24 (See https://en.wikipedia.org/wiki/24_Game).

App takes in any number of comma-separated numbers and brute forces a solution using all the numbers to find a way to calculate 163.

Ex:
2,4,5,9,11,15 --> (11*15)-(9-5)/(4-2)=163

*General Strategy for 163:*

13\*13=169 is the closest square to 163. Using the difference of two square, it is very easy to calculate products such as 11\*15=(13-2)(13+2)=13^2-4=165. Once close to 163, check remaining numbers to get to 163.


*Current features:*
- Show 1 possible solution for any number of cards to solve for 163

*To do:*
- Show multiple solutions
- Solve for any other number
- Improved runtime
