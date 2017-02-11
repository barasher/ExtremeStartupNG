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

### Java

FIXME

### Docker

FIXME

## Configuration

**Extremestartupng-referee** can be configured through a YAML file.

FIXME

Here is a sample :

```yaml
server:
  port: 8081
esng:
  questions:
    -
      family: "aFamily"
      enabled: false
    -
      family: "anotherFamily"
      level: 3
```


## Classical workflow

### Candidate registering

To get questions, candidates have to register to the referee through a REST webservice.

Once the new candidate has been registered, the **extremestartupng-referee** starts asking him questions.

#### Input

* **Method** : GET
* **Path** : addPlayer
* **QueryParams**
  * **_nick_** : (required) Nickname of the candidate
  * **_host_** : (required) Host of the candidate's **extremestartupng-candidate**
  * **_port_** : (required) Port of the candidate's **extremestartupng-candidate**
* **Sample** : `http://192.168.0.1:8080/addPlayer?nick=john&host=192.168.0.2&port=8081`

#### Output

```json
{
  "_nick" : "john",
  "_uri" : "http://192.168.0.2:8081",
  "_score":0
}
```
### Listing registered players

This service returns every registered candidates.

#### Input

* **Method** : GET
* **Path** : players
* **Sample** : `http://192.168.0.1:8080/players`

#### Output

```json
[ 
  {
    "_nick" : "john",
    "_uri" : "http://192.168.0.2:8081",
    "_score" : 120
  }
]
```

### Changing level

This service changes the current level.

#### Input

* **Method** : GET
* **Path** : changeLevel
* **QueryParams**
  * **_level_** : New level (if unspecified, the current level will be increased)
* **Samples** :
  * `http://192.168.0.1:8080/changeLevel` : if the current level is 3, the new one will be 4
  * `http://192.168.0.1:8080/changeLevel?level=5` : change level to level 5 

#### Output

```json
{
  "_currentLevel" : 5,
  "_players" : [
    {
      "_nick" : "john",
      "_uri" : "http://192.168.0.2:8081",
      "_score" : 150
    }
  ]
}
```

### Getting metrics

Several metrics are available during test.

#### Input

* **Method** : GET
* **Path** : metrics
* **Sample** : `http://192.168.0.1:8080/metrics`

#### Available metrics

* **gauge.extremeStartupNG.currentLevel** : current level,
* **counter.extremeStartupNG.questions.count** : question count since the beginning of the test,
* **counter.extremeStartupNG._[nickname]_.incorrectAnswer.count** : incorrect answer count,
* **counter.extremeStartupNG._[nickname]_.correctAnswer.count** : correct answer count.


#### Output


```json
{
  "gauge.extremeStartupNG.currentLevel": 2.0,
  "counter.extremeStartupNG.questions.count": 16,
  "counter.extremeStartupNG.john.incorrectAnswer.count": 3,
  "counter.extremeStartupNG.john.correctAnswer.count": 8
}
```
The current level is 2, 16 questions have been asked, John gave 3 incorrect answers and 8 correct answers. 16 - (3 + 8) questions were already asked when John joined the test.

