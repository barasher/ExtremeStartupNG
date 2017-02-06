# ExtremeStartupNG

## Presentation

ExtremeStartup is a technical test for developers recruitment.
The principle is to go beyond usual physical interview by asking the candidate to write code to solve technical problems.
With ExtremeStartupNG, recruters can avoid recruiting people that are good at talking but inneficient at programming.

## Principles

The **extremestartupng-referee**'s job is to ask a new question to **extremestartupng-candidate** and check its response every 5 seconds.
A success mark and a failure mark is associated to each question :
- If the answer is correct, the candidate global score is incremented by the question success mark
- If the answer is wrong, the candidate global score is decremented by the question failure mark

### ExtremeStartupNg-Referee

The **extremestartupng-referee** is a REST webservice that can be onsidered as the test referee :
- Candidates register to the referee
- Level is changed through the referee

