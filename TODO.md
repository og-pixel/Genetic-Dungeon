# List of things to keep a track on
- [ ] Add Logger and write information that way
- [ ] Clean most TODO's
- [ ] Create some sort of "menu" where  I can directly control app better (but still by arguments only)
- [ ] Try to create basic manual corection where we look for rooms of certain size (square stuff or summin)
- [ ] Try to split program into making rooms only
- [ ] Create one map by hand (that looks like dungeon) and run this one as controlled environment
(also to check whenever I can get something ***Interesting*** out of already made maps)
- [ ] In report mention, problems with good evaluation
- [ ]
- [ ]
- [ ]
- [ ]

# Acttual things to do in 2-3 days tops
## REALLY work on selection, so far i only pick top 10%, always. There are multitudes of selections and I s    hould try implement my own
- [ ] one fixed point (rolletue wheel)
- [ ] two fixed points (Stochastic Universal Sampling)
- [ ] Tournament k (choose random k, elements, get best)
- [ ] Rank Selection (no fitness score, but choose more likely based on rank)
- [ x ] Create implementation of all 4

- [ ] population size should be kept small as it slows down GA significantly, however a trial and error is needed to determinate the smallest possible size that still works
- [ ] I can either replace populations by entire generations (like I do now) or slowly one parent/children at the time,
- [ ] fitness function might be using approximation (or better, use approximation if calcuation is slow, define slow)
- [ ]  good diversity is a problem (or a lack of there of, I start with full random, but it feels less so at the end)
- [ ] make EvolutionResult to print a specific value at specfici place (get console printing i guess)
- [ ] crossover is a separate thing
- [ ] Double point crossover
- [ ] No Crossover (total random)
- [ ] THERE IS MUCH MORE
##TODO MUTATION if mutation is high, its just random (so i guess 1%-3% is top) WITHOUT MUTATION THERE IS NO SE    ARACH
- [ x ] Mutation by swapping values (what i do now
##TODO PREMUTATIONS
- [ ] Swap inside the same chromosome (premutation?)
- [ ] Scramble
- [ ] Inversion
##TODO I have no selection (I just pick top 10%, also called Elitysm)
- [ ] Age Based (I dont like it, it does not base fitness)
- [ ] Fitness based (but children replace the least fit, it more fitting)


##TODO I usually dont need to iterate so long (number of generations) I will make my number a generations a ro    of (so it should try to terminate sooner)


