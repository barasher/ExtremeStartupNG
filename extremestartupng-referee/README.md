# ExtremeStartupNG-Referee

## Presentation

The **extremestartupng-referee** is the referee for the recruitment test.

It asks a new question every 5 seconds to every registered candidate. Every question belongs to a question family. A level is associated to each question family. The **extremestartupng-referee** holds a current level to determine which questions can be asked. If the current level is _x_ then questions will be picked from level 1 to level _x_ families.

> Let's consider two question families :
- _CharToIntFamily_ is a _char_ to _int_ conversion question (What is the integer value for character 'a' ?). It's associated level is 1
- _DecimalToBinary_ is a _decimal_ to _binary_ number conversion (What is the binary value for 12 ?). It's associated level is 2.

>Every 5 seconds, a new question corresponding to current level will be randomly generated. The current level of the **extremestartupng-referee** is 1, so only _CharToIntFamily_ questions will be generated :
- _(t)_ : What is the integer value for character 'c' ?
- _(t+5s)_ : What is the integer value for character 'w' ?
- _(t+10s)_ : What is the integer value for character 'k' ?
- ...

> When the current level is increased, changing to level 2, question will be generated from level 1 and level 2 question families :
- _(t)_ : What is the binary value for 10 ?
- _(t+5s)_ : What is the binary value for 5 ?
- _(t+10s)_ : What is the integer value for character 'n' ?
- _(t+15s)_ : What is the binary value for 12 ?

Each family question has a success and a failure mark. If a candidate answer is correct, the success mark will be added to its score. Otherwise, it is the failure mark that will be added.

> Let's consider that the _CharToIntFamily_ question family's success mark is 10 and that its failure mark is -20 :
- _(t)_ : What is the integer value for character 'c' ? 42 => **incorrect** => score : -20
- _(t+5s)_ : What is the integer value for character 'w' ? 38 => **incorrect** => score : -40
- _(t+10s)_ : What is the integer value for character 'p' ? 112 => **correct** => score : -30

## Execution

TODO

## Classical workflow

### Candidate registering

To get questions, candidates have to register to the referee through a REST webservice.

irst Header | Second Header
------------ | -------------
Content from cell 1 | Content from cell 2
Content in the first column | Content in the second column


**Method** | GET
------------ | -------------
**Path** | addPlayer
**QueryParam _nick_ (required)** | Nickname of the candidate
**QueryParam _host_ (required)** | Host of the candidate's **extremestartupng-candidate**
**QueryParam _port_(required)** | Port of the candidate's **extremestartupng-candidate**
**Sample** | _http://192.168.0.1:8080/addPlayer?nick=john&host=192.168.0.2&port=8081_

TODO output

Once the new candidate has been registered, the **extremestartupng-referee** starts asking him questions.

### Listing registered players

TODO

### Changing level

TODO

### Getting metrics

TODO
