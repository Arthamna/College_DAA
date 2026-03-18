
Table Of Content :
* 1. [Correctness](#Correctness)
		* 1.1. [Example : Insertion Sort](#Example:InsertionSort)
* 2. [Complexity](#Complexity)
		* 2.1. [Example : Insertion Sort](#Example:InsertionSort-1)
* 3. [Understanding](#Understanding)
* 4. [Abstraction](#Abstraction)
* 5. [Designing](#Designing)
		* 5.1. [Forward Design](#ForwardDesign)
		* 5.2. [Backward Design](#BackwardDesign)
		* 5.3. [Intuitive Modeling](#IntuitiveModeling)
	* 5.1. [General Strategies](#GeneralStrategies)
		* 5.1.1. [Incremental](#Incremental)
		* 5.1.2. [Greedy](#Greedy)
		* 5.1.3. [Divide and Conquer](#DivideandConquer)
		* 5.1.4. [Dynamic Programming](#DynamicProgramming)
		* 5.1.5. [Number Theoretic](#NumberTheoretic)
* 6. [Analysis](#Analysis)
		* 6.1. [Correctness](#Correctness-1)
		* 6.2. [Complexity](#Complexity-1)
* 7. [Implementation](#Implementation)

---


# Algorithm

Procedure & Computational section <br>
2 Important things about Algorithm Desain :
## Correctness

The algo must produce correct output for every possible valid input. How do we prove it ?
- Proving Methods (Discrete Math, e.g. Direct proof, Indirect proof) 
	- This will take time because we have to formulation first
<br>

- Loop Invariant (Cormen, Intro to Algorithms, [Here](https://www.cs.mcgill.ca/~akroit/math/compsci/Cormen%20Introduction%20to%20Algorithms.pdf)) 
	- Initialization : It is true prior to the first iteration of the loop
	- Maintanance : If it is true before an iteration of the loop, it remains true before the next iteration
	- Termination : When the loop terminates, the invariant gives us a useful property that helps show that the algorithm is correct.
		- Simpler terms : identify a property that is true before and after each iteration of a loop, and show it implies the correct result when the loop ends.

#### Example : Insertion Sort

```pseudocode
for j = 2 to A.length // start from 2nd element
	// pick up the card
	key = A[j] 
	// start comparing from the left
	i = j - 1 
	while i > 0 and A[i] > key 
		// shift bigger elements right
		// will keep going to left 
		// if current element > pointer element (key)
		A[i + 1] = A[i]
		i = i - 1
	A[i + 1] = key // insert the card
```

> But, wait, how to make the pseudocode ?  Here : [Abstraction](#abstraction)

<br>
Loop invariant analysis 

> Think of them as : "What is always true about the state of computation at this point ?"

Invariant condition :
At start of each iteration `for loop`, the subarray `A[1..j−1]` consists of the elements originally in `A[1..j−1]`, but in sorted order

- Initialization (j = 2) :  `A[1..1]` contains just one element. A single element is trivially sorted
- Maintenance : Assume `A[1..j-1]` is sorted. The inner while loop shifts elements larger than key (on one position right), then places key in correct spot. After this, `A[1..j]` is sorted, maintaining the invariant for the next iteration
- Termination (j = n+1) : Ends when j > n. Because each iteration increases j by 1, this will achieved. At that point, subarray `A[1..n]`consists of elements originally in `A[1..n]`, and in sorted order. Because the subarray is the entire array, we conclude that the entire array is sorted. Hence, the algorithm is correct.
 
<br>

## Complexity

Rule of thumb (RAM Model) :
- Each "simple" instruction takes exactly ONE time step
	- arithmetic (+, −, ×, /, mod)  
	- data mov (assignment, accesing array)
	- control flow (if, function call, return)
- Each memory access (reading / writing any cell) takes ONE time step (or memory step, if it make sense)
<br>

To find running time, where `T(n)`  is a function of input size n :
- Count how many times each line of the algorithm executes
- Assign a cost to each line
- Sum up all the costs

#### Example : Insertion Sort

We'll use `//` as separator for counting `cost` and `times executed`

Let $t_j$ be the number of times the while loop body executes for a given `j` (we need to "variable" this because it's uncertain value (like `n`) ) 

```pseudocode
for j = 2 to A.length // c1 // n
	key = A[j] // c2 // n-1
	i = j - 1 // c3 // n-1
	while i > 0 and A[i] > key // c4 // Σ (j=2 ... n) tj 
		A[i + 1] = A[i] // c5 //  Σ (j=2 ... n) (tj-1)
		i = i - 1 // c6 // Σ (j=2 ... n) (tj-1)
	A[i + 1] = key // c7 // n-1
```
<br>

`Σ (j=2 ... n) <=> n as upper bound, and j=2 as lower bound`
<br>

It's not multiplied by sum of $t_j$, it's the input `j`, e.g  $t_2$ , $t_3$ ..., $t_n$ 
<br>

How to actually count t at that index ? How much the index is swapped
<br>

So, the total running time is :

```
T(n) = c1n + c2(n−1) + c3(n−1) + c4 Σ(j=2.. n)tj + c5 Σ(j=2.. n)(tj−1) + c6 Σ(j=2.. n)(tj−1) + c7 (n−1)
```

Let's check the best and worst case :
##### Best Case
Array is already sorted <br>
$t_j$ = 1 (because we only run it once, the while cond. never happens)

c4 becomes : `Σ(j=2.. n) 1 = 1 + 1 + ... + 1 (for n-1 (index j=2) ) <=> n-1` 

So it becomes :
```
T(n) = c1n + (c2 + c3 + c4 + c7)(n − 1)  
<=> (c1 + c2 + c3 + c4 + c7)n -  (c2 + c3 + c4 + c7)
<=> an + b 
```
for constants a, b, Which linear : `O(n)`


##### Worst Case
Decreasing order, compared against all previous sorted elements. <br>
$t_j$ = j

```
c4 Σ(j=2.. n)tj <=> c4 Σ(j=2.. n) j
c4 (j2 + j3 + j4 ... jn)
```

Remember sum of increasing number ?

`1 + 2 + .. + 50 = 50(51)/2`

```
Σ(j=2.. n) j
<=> Σ(k=1.. n) - 1 // ((2 + ... + n) = (1 + .. + n) -1 )
c4 (n(n+1)/2 − 1)
```

Since there is $n^2$ , T(n) will be quadratic : `O(n^2)`

<br>

# Framework

## Understanding

- Problem ?
- Input ?
- Output ?
- Keyword (Regularity/Hint) ?
- 
## Abstraction

How to find right pseudocode for our problem ?
Let's break it into sequential steps :
- Paradigm 
	- Common Pattern (or regularity) ?
- Theory
	- Discrete Math theory, Number theory, etc

## Designing

After we can identify the pattern, there are some way to construct the pseudocode (algorithm)
#### Forward Design

In a nutshell :
```
Given case N that produce K ->
Examination from N ->
Pattern formulation
```
#### Backward Design

In a nutshell :
```
Given case N that produce K ->
Examination from K ->
Pattern formulation
```

For example :
If there is K, what is the max of N ?
#### Intuitive Modeling

Ad hoc ( *latin* : for this) solution. Need understand related theory to formulate the solution, which is only for that problem. 

### General Strategies

#### Incremental
Build the solution one piece at a time. Process elements one by one, maintaining some running state. <br>
**Example**: finding the maximum in an array by scanning left to right, updating the max as you go.

#### Greedy
At each step, make the locally optimal choice, hoping it leads to the globally optimal solution.   <br>
**Works when**: the problem has the “greedy choice property” (a local optimum leads to a global optimum).  <br>
**Example**: choosing the shortest remaining edge in Kruskal’s algorithm for minimum spanning trees.

#### Divide and Conquer
Break the problem into smaller sub-problems of the same type, solve them recursively, then combine the results. <br>
**Example**: Merge Sort splits the array in half, sorts each half, then merges.
#### Dynamic Programming

Like divide-and-conquer, but sub-problems OVERLAP (the same sub-problem is needed multiple times).  Store results of sub-problems to avoid recomputation. <br>
**Example**: Fibonacci numbers. Naive recursion recomputes
the same values exponentially many times; DP stores them and computes each only once.
#### Number Theoretic
Some problems have elegant mathematical solutions that bypass brute-force entirely. <br>
**Example**: computing GCD with Euclid’s algorithm in O(log n) instead of checking all divisors.

<br>

### Summary

| If you see...                                      | Think...                                 | Example            |
|----------------------------------------------------|------------------------------------------|--------------------|
| "Find min/max of something"                        | Single pass, running aggregate           | Columns, Journey   |
| "Modular arithmetic, divisibility"                 | GCD, number theory properties            | Unique Numbers     |
| "Construct the optimal X"                          | Greedy: most-significant first           | Min/Max Numbers    |
| "Sequence doubles each step"                       | Binary representation, popcount          | Interesting Sequence |
| "f(n) depends on f(n/a) + f(n/b)"                  | Memoization with map (sparse DP)         | Recursive Function |
| "n is huge but few distinct states"                | Map-based DP, not array                  | Recursive Function |


Then we can go back to algorithm analysis here : [Correctness](#correctness)
(Below section is left empty, I'll use it as report template)

## Analysis
#### Correctness 
#### Complexity  

## Implementation



