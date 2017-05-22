-- This is the file where you should write your functions to turn in

-- Comments look like this. Please add comments to describe your code

add_and_tripple x y = 3 * ( x + y )
(+*) x y = add_and_tripple x y

triangular_numbers n = do
  [floor(x * (x + 1) / 2) | x <- [1..n]]
