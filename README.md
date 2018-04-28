A simple library to do a parallel foldMap/map-reduce over multiple cores with error accumulation semantics.

This tutorial explains how to fold a generic type with the Foldable and Monoid type classes:

http://eed3si9n.com/herding-cats/using-monoids-to-fold.html

A foldMap is simply a map followed by a fold. In order to do a parallel foldMap, we first divide the Foldable into chunks 
equal to the number of cores on the machine. The idea of chunking a type is represented by the Chunkable type class. 
We then execute foldMap on each chunk in parallel across the cores using Futures. If a single Future fails, we accumulate errors
across all futures. If all Futures are successful, we again fold over the values returned by each Future to arrive at the     
required output. I wrote a blog post explaining how error accumulation from a collection of Futures works:

https://medium.com/musings-on-functional-programming/error-accumulation-with-collection-of-futures-82fb4da47466


I implemented my own type classes and instances rather than using existing ones from cats or scalaz to get a better
understanding of how they work. Some examples on how to use the library can be found in the **exampleapps** package.
Please feel free to add your type classes/instances and exampes.
