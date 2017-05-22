{-|
  | This module implements a binary tree that satisfies the properties of a maximum heap.
  | The heap is used as a priority queue, with the highest priority found at the root of
  | the heap. The heap holds patient information, including the patient's name, an
  | integer priority, and the amount they owe.
  |
  | The module has the expected insert, delete, and peek that works with single patients.
  | In addition, the module supports several other operations to get information about
  | the heap's current properties.
-}
 --let h = (HeapNode ("Raul", 150, 4) (HeapNode ("LOL", 130, 6) Tip Tip) Tip)
 --let nh = (HeapNode ("Bitch", 90, 190) (HeapNode ("The Cuck", 80, 210) Tip Tip) Tip)

module PA1 where

type Patient = (Name, Priority, Bill)
type Name = String
type Priority = Int
type Bill = Double
-- data type for a binary tree where Patient values are stored at each HeapNode and
-- Tips are valueless leaf node markers
data Heap a = Tip | HeapNode a (Heap a) (Heap a) deriving (Eq, Ord, Show)

-- insert a Patient value into the heap by merging a heap with only the patient with the rest of the heap
insert :: Patient -> Heap Patient -> Heap Patient
insert p h = merge (HeapNode p Tip Tip) h

-- delete the root of the heap and merge the two child nodes
delete :: Heap Patient -> Heap Patient
delete (HeapNode p l r) = merge l r

-- get the priority of a patient
getPriority :: Patient -> Priority
getPriority (_,prio,_) = prio


--let h = HeapNode (“Raul”, 150, 4) (HeapNode (p1 Tip Tip)) (HeapNode (“Zara”, 60, 200) Tip Tip)

-- merge two heaps together while maintaining heap properties
merge :: Heap Patient -> Heap Patient -> Heap Patient
merge Tip a = a
merge a Tip = a

merge (HeapNode patient1 l1 r1) (HeapNode patient2 l2 r2)
  |getPriority(patient1) > getPriority(patient2) = (HeapNode patient1 (merge heap2 r1) l1)
  |otherwise     = (HeapNode patient2 (merge heap1 r2) l2)
  where heap2 = (HeapNode patient2 l2 r2)
        heap1 = (HeapNode patient1 l1 r1)
-- return the patient with the highest priority
peek :: Heap Patient -> Patient
peek Tip = ("Imaginary", -1, -1)
peek (HeapNode a _ _) = a

-- convert a heap to a list of patients, with the list showing the tree from left to right
heap2InList :: Heap Patient -> [Patient]
heap2InList Tip = []
heap2InList (HeapNode p l r) = (heap2InList l) ++ (heap2InList r) ++ [p]

-- convert a heap to a list of patients with each node printed before its children
heap2PreList :: Heap Patient -> [Patient]
heap2PreList Tip = []
heap2PreList (HeapNode p l r) = [p] ++ (heap2PreList l) ++ (heap2PreList r)
-- extract all of the patient names in order of top-to-bottom, left-to-right
heap2Names :: Heap Patient -> [Name]
heap2Names Tip = []
heap2Names (HeapNode (p1name, _, _) l r) = [p1name] ++ (heap2Names l) ++ (heap2Names r)
-- return whether the heap has no patients in it
isEmpty :: Heap Patient -> Bool
isEmpty Tip = True
isEmpty a   = False
-- return the height of the heap
height :: Heap Patient -> Int
height Tip = 0
height (HeapNode _ l r) = 1 + max (size l) (size r)

-- return the heap with a function applied to all Bills in the heap
heapMap :: (Bill -> Bill) -> Heap Patient -> Heap Patient
heapMap _ Tip = Tip
heapMap func (HeapNode (name, pri, bill) l r) = (HeapNode (name, pri, (func bill)) (heapMap func l) (heapMap func r))
size :: Heap Patient -> Int
size Tip = 0
size (HeapNode _ l r) = 1 + size(l) + size(r)
