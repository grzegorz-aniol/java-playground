# Java playground

Project with personal tests on different Java aspect (e.g. performance, design patterns)

### Performance tests

#### Unique ID generation 

**Run complete. Total time: 00:02:14**

```
Benchmark                               Mode  Cnt    Score    Error  Units
IdGeneratorPerfTest.testFullUUIDKey     avgt   20  746,844 ? 19,603  ns/op
IdGeneratorPerfTest.testRandomId        avgt   20   26,835 ?  0,418  ns/op
IdGeneratorPerfTest.testShortedUUIDKey  avgt   20  747,583 ? 10,945  ns/op
```

#### Put elements into HashMap

Adding objects into an empty map in a loop
```
Benchmark                               Mode  Cnt       Score       Error  Units
HashMapOpsTest.add100ElementToHashMap   avgt    6     943.715 ±   234.539  ns/op
HashMapOpsTest.add1KElementToHashMap    avgt    6    8891.599 ±   527.482  ns/op
HashMapOpsTest.add10KElementToHashMap   avgt    6   93021.570 ± 14250.990  ns/op
HashMapOpsTest.add100KElementToHashMap  avgt    6  998528.073 ± 40830.177  ns/op
```

Adding one object to a map of specific size
```
Benchmark                             Mode  Cnt   Score   Error  Units
HashMapAddTest.addElementToMapOf100   avgt    6  18.185 ± 2.084  ns/op
HashMapAddTest.addElementToMapOf1k    avgt    6  19.166 ± 2.455  ns/op
HashMapAddTest.addElementToMapOf10k   avgt    6  25.573 ± 3.338  ns/op
HashMapAddTest.addElementToMapOf100k  avgt    6  24.652 ± 1.737  ns/op
```