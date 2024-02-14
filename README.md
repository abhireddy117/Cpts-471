CPTS 471 - Computational Genomics

**Project 1: Pairwise Sequence Alignment**
- Implements **Needleman-Wunsch global alignment** and **Smith-Waterman local alignment algorithms** to align two biological sequences
- Accepts input sequences in FASTA format and scoring parameters via config file
- Generates optimal pairwise alignment matrix and traces back highest score path
- Produces sequence alignments indicating matches, mismatches and gaps
- Calculates alignment statistics including score, identities, gaps and percent identity
= Formats and prints aligned sequences and stats for analysis
- Identifies conserved regions and evolutionary relationships between sequence pairs
= Provides core functionality for determining functional and evolutionary similarities between DNA or protein sequences


**Programming Project #2: Suffix tree construction**
- Provides two construction algorithms - **Ukkonen's and McCreight's **
- Supports traversal methods like depth-first search
- Analyzes suffix trees to find average depth, longest repeated substrings etc.
- Reads genomic sequence data from FASTA files as input
- Useful for testing and analyzing suffix tree algorithms
- Can be enhanced by adding more analysis functions, optimizations etc.
- Good demonstration of tree data structures and algorithms
- Serves as a library for suffix tree construction and analysis
- Enables experimenting with different construction techniques
- Input sequences allow testing on real genomic datasets

**Programming Project #3: Genome-scale comparisons for Covid strains**

- Constructs a generalized suffix tree by combining multiple DNA sequences with separator symbols and incrementally inserting all suffixes.
- Splits existing tree edges when incoming suffix does not match, handles mismatches efficiently.
- Stores start/end indices, suffix index, string depth for each node.
- Calculates statistics like number of nodes, average depth of internal nodes for analysis.
- Finds and prints longest repeated substring based on deepest internal node.
- Demonstrates efficient construction, traversal and analysis methods on suffix trees for pattern matching across lengthy DNA sequences.
