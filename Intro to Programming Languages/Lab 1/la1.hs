-- This is the file where you should write your functions to turn in

-- Comments look like this. Please add comments to describe your code

removeElems :: (Integral a) => a -> [a] -> [a]


removeElems lookfor [] = []
removeElems lookfor (x:xs)
  |x==lookfor = removeElems lookfor xs
  |otherwise  = x : (removeElems lookfor xs)

longerThan :: (Integral a) => Int -> [[a]] -> [[a]]



longerThan len (front:back)
  |(length back == 0 && length front > len) = [front]
  |(length back == 0 && length front <= len) = []
  |(length front) > len = front:(longerThan len back)
  |otherwise = longerThan len back

printBar :: (Integral a) => [a] -> String
printBar list
  |(length list) >= 1 = "*" ++ (printBar back)
  |otherwise = ""
  where back = (tail list)

sin' :: (Fractional a) => Rational -> Int -> a
sin'' :: (Fractional a) => Rational -> Int -> Int -> Bool -> a

factorial :: Int -> Int

factorial 1 = 1
factorial n = n * (factorial (n - 1))

sin' x approx = fromRational( x - (sin'' x approx 3 False) )

sin'' x approx counter neg
  |approx == 0 = fromRational(toRational (x^counter) / toRational(factorial counter))
  |neg==True   = fromRational(toRational (x^counter) / toRational(factorial counter) - (sin'' x (approx-1) (counter+2) False))
  |otherwise   = fromRational(toRational (x^counter) / toRational(factorial counter) + (sin'' x (approx-1) (counter+2) True))


toAstro :: Int -> Int -> String

toAstro month day
  |month > 12 || month < 0 || day < 0 || day > 31 = error "Invalid Date"
  |total >= (3*31+21) && total <= (4*31+19) = "Aries"
  |total >= (4*31+20) && total <= (5*31+20) = "Taurus"
  |total >= (5*31+21) && total <= (6*31+22) = "Gemini"
  |total >= (6*31+22) && total <= (7*31+22) = "Cancer"
  |total >= (7*31+23) && total <= (8*31+22) = "Leo"
  |total >= (8*31+23) && total <= (9*31+22) = "Virgo"
  |total >= (9*31+23) && total <= (10*31+22) = "Libra"
  |total >= (10*31+23) && total <= (11*31+21) = "Scorpio"
  |total >= (11*31+22) && total <= (12*31+21) = "Sagittarius"
  |total >= (12*31+22) && total <= (1*31+19) = "Capricorn"
  |total >= (1*31+20) && total <= (2*31+18) = "Aquarius"
  |total >= (2*31+19) && total <= (3*31+20) = "Pices"
  |otherwise = error "Invalid Date"
  where total = month*31+day
