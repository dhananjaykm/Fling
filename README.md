# Fling It!

Fling! is a puzzle game. In the start, You are given certain number of balls arranged on 8 x 7 grid. You have to remove all ball except one. Only action you can do is fling a ball in up, down, left or right direction. The flinged ball should hit another ball, that ball might hit yet another ball and so on. Last ball which get hit will move out of grid and every other ball will take position just beside the ball which it hit. You can't fling ball in a direction when there is adjacent ball in that direction. 

### Prerequisities
Java SE installed on machine.
Environment variable PATH should be set to point to where the Java binaries have been installed.


### Installing

No installion required.

### Run
Set up java bin path

```
compile : javac filename.java
run     : java filename 
```
OR
```
copy complete java code and run on online compilers eg: http://ideone.com/
```

## Sample tests
```
.......
.......
.......
.......
..A..cd
.......
.......
.......
```
```
..B....
.....C.
.......
.......
..A....
.......
.......
.......
```

```
A......
.......
.......
.......
.......
.......
.......
.......
```
```
....A..
.......
.......
B......
....c..
...D...
.......
.......
```


## Motivation

1. design and algorith : http://www.caam.rice.edu/~timwar/TWCourses/CAAM420_files/CAAM420F09HW3.pdf 
2. copy 2d array in java: http://stackoverflow.com/questions/5617016/how-do-i-copy-a-2-dimensional-array-in-java

## Algorithm
A recursive function to solve 8X7 Fling board puzzle
```
	function move(B)
	% purpose: test every possible move recursively on the Fling! board
	% if there is just one ball left we are done and should print out
	if(B.num_fur_balls ==1)
		print_move_history(B)
	end

	% loop over all board rows
	for r=1:B.Nrows
		% loop over all board columns
		for c=1:B.Ncols
			% is there a ball to move at position (r,c) ?
			if( get_entry(B, r, c) == 1)
			% loop over four possible move directions

			for dir=0:3
				% can this ball move in direction dir ?
				if(can_move(B, r, c, dir))
					% create a deep copy of the current board
					newB = copy_board(B);
					% make ball move. Also record this move in history stored in newB
					newB = apply_move(newB, r, c, dir);
					% now try next move on this new board
					move(newB);
				end
			end
		end
	end
end
```

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.


## Authors

* **Dhananjay Kumar** 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to Helpshift motivation
* Hat tip to anyones who's codes used from stackoverflow
