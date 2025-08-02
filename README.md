# ASD – Programming Task 3

## Problem Description

You are given a sequence of natural numbers and a pointer `P` initially pointing to the first element. The program must perform `k` operations based on the value at the pointer's position.

### Operations

- **ADD**: If the value at position `P` is odd:
  - Insert `X-1` at position `P+1`
  - Move pointer `P` `X` steps to the right
- **DELETE**: If the value at position `P` is even:
  - Remove the element at position `P+1`
  - Move pointer `P` `X` steps to the right

All pointer movement is **cyclical**.

## Input

- A file passed as the **first argument** to the program.
- The file contains:
  - Line 1: number of operations `k`
  - Line 2: the initial sequence of natural numbers

## Output

- A single line containing the modified sequence (after all operations),
  printed **cyclically starting** from the pointer `P`.

## Example

**Input:**
