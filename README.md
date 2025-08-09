## LinkStrand: DNA Splicing with Linked Lists

A Java project that models DNA strands and simulates restriction enzyme cut-and-splice operations. It compares three implementations of the `IDnaStrand` API:

- `StringStrand`: stores the strand as a single `String` (simple, slow for many appends)
- `StringBuilderStrand`: stores the strand in a single `StringBuilder` (faster appends)
- `LinkStrand`: stores the strand as a linked list of nodes (efficient splicing/append patterns)

This repository includes a benchmarking program and JUnit tests to analyze correctness and performance, using E. coli genome data provided in `p3-dna/data/`.

### Project structure

- `p3-dna/src/`
  - `IDnaStrand.java`: interface (API) for all strand implementations
  - `StringStrand.java`, `StringBuilderStrand.java`, `LinkStrand.java`: three concrete implementations
  - `CharDnaIterator.java`: iterator over a strand (uses `charAt`)
  - `DNABenchmark.java`: benchmark driver for `cutAndSplice`
  - `TestStrand.java`, `TestIterator.java`: JUnit tests
- `p3-dna/data/`: `ecoli_small.txt` and `ecoli.txt` datasets
- `p3-dna/lib/`: JUnit jars

### Prerequisites

- Java 11+ (Java 17 recommended)

### Quick start (benchmark)

1) Clone and enter the project directory (or use your existing checkout):

```bash
git clone <this-repo-url>
cd <repo-folder>/p3-dna
```

2) Compile only the runtime sources (exclude tests so you don’t need the JUnit classpath):

```bash
mkdir -p out
javac -d out src/IDnaStrand.java src/StringStrand.java src/StringBuilderStrand.java \
  src/LinkStrand.java src/CharDnaIterator.java src/DNABenchmark.java
```

3) Choose which implementation to benchmark by editing `DNABenchmark.java` and setting `strandType` to one of:

```java
//private static final String strandType = "StringStrand";
private static final String strandType = "StringBuilderStrand";
//private static final String strandType = "LinkStrand";
```

4) Run the benchmark (defaults to `data/ecoli_small.txt`):

```bash
java -cp out DNABenchmark
```

To benchmark the full E. coli dataset, change the `fileName` in `DNABenchmark.main` from `data/ecoli_small.txt` to `data/ecoli.txt` (this can take longer).

### Run the JUnit tests

1) Compile production classes (if not already):

```bash
cd p3-dna  # if not already in this directory
mkdir -p out
javac -d out src/IDnaStrand.java src/StringStrand.java src/StringBuilderStrand.java \
  src/LinkStrand.java src/CharDnaIterator.java
```

2) Compile tests with the JUnit classpath:

macOS/Linux:
```bash
javac -cp "lib/junit-jupiter-api-5.8.2.jar:lib/opentest4j-1.2.0.jar:out" -d out \
  src/TestStrand.java src/TestIterator.java
```

Windows (CMD):
```bat
javac -cp "lib\junit-jupiter-api-5.8.2.jar;lib\opentest4j-1.2.0.jar;out" -d out ^
  src\TestStrand.java src\TestIterator.java
```

3) Run tests using the JUnit Console Launcher:

```bash
java -jar lib/junit-platform-console-standalone-1.8.2.jar --class-path out --scan-class-path
```

### Usage example

```java
IDnaStrand dna = new LinkStrand("cgatcctagatcgg");
IDnaStrand recomb = dna.cutAndSplice("gat", "gggtttaaa");
System.out.println(recomb.toString());
```

### Notes on performance

- `cutAndSplice` builds a new strand by repeatedly calling `append`. The runtime depends heavily on `append` complexity in the chosen implementation.
- `LinkStrand` keeps a linked list of nodes, making repeated appends and certain splice patterns more efficient for large splicee strings and many breaks.
- `charAt` in `LinkStrand` is implemented with caching so sequential access via the iterator is efficient.

### Acknowledgments

Adapted from Duke University CompSci 201 Project 3 materials. Starter data and figures belong to their respective owners and are used here for educational purposes.


