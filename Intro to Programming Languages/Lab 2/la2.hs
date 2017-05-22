module PolyLA2 (Coeff, Exp, Polynomial) where  -- defines the module name so you can import from another haskell file

type Coeff=Int
type Exp=Int
type Polynomial=[(Coeff, Exp)] -- a list of terms in a polynomial formula


-- TODO: implement addpoly to add two polynomials



addpoly::Polynomial->Polynomial->Polynomial

addpoly nonnull [] = nonnull
addpoly [] nonnull = nonnull

addpoly ((coeff1, exp1):poly1rest) ((coeff2, exp2):poly2rest)
	|exp1==exp2 = (coeff1+coeff2,exp1):(addpoly poly1rest poly2rest)
	|exp1 < exp2 = (coeff1,exp1):(coeff2, exp2):(addpoly poly1rest poly2rest)
	|otherwise = (coeff2,exp2):(coeff1, exp1):(addpoly poly1rest poly2rest)








--TODO: implement multpoly to multiply two polynomials
multpoly::Polynomial->Polynomial->Polynomial

multpoly ((coeff1, exp1):poly1rest) poly2 = addpoly  (multpoly poly1rest poly2) (multpoly' coeff1 exp1 poly2)
multpoly nonnull _ = nonnull

multpoly' coeff1 exp1 ((coeff2, exp2):poly2rest) = (coeff1*coeff2, exp1+exp2):(multpoly' coeff1 exp1 poly2rest)
multpoly' _ _ [] = []


