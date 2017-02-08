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

### ExtremeStartupNG-Referee

The **extremestartupng-referee** is a REST webservice that can be considered as the test referee :
- Candidates register to the referee
- It asks questions
- Level is changed through the referee
- Metrics can be retrieved through the referee

See **extremestartupng-referee** project page for [more information](extremestartupng-referee/README.md).

### ExtremeStartupNG-Candidate

The **extremestartupng-candidate** that has to be cloned by candidates : it is the maven project that candidats have to complete to answer questions asked by the **extremestartupng-referee**.

See **extremestartupng-candidate** project page for more information.

### ExtremeStartupNG-Common

The **extremestartupng-common** gathers all source code shared between **extremestartupng-referee** and **extremestartupng-candidate**.
