# Planista
Program to simulate how different strategies of system scheduler might work.

Reads from stdin data in format:

integer: number of processes

for every process:

two integers: moment when process show up, and how much time it need to end.

(At least one process will show up in moment=0)

integer: how many different round robins strategies should be used

for every round robin: integer: what should the round-robin parameter be equal to.

Example data:
```
5 //no of processes
0 10 //first process some up at moment =0 and requires 10 units
0 29
0 3
0 7
0 12
2 // 2 different round robin strategies
1 10 //first one will give each process 1 time unit, second one will give 10 time units instead
```

warning: code/out is in polish only.
