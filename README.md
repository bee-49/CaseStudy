# TargetCaseStudy - Document Search
 Author: *Brittany Schembra*

## Running the Program
use the following command to run the program: <br />
java DocumentSearch.java sample_text/french_armed_forces.txt  sample_text/hitchhikers.txt  sample_text/warp_drive.txt

## Performance Testing
Perfomance testing was done inside main and is commented out. I used a text file that has 2M random words and <br />
is placed in sample_text folder as wordlist.txt. Here are the results: <br />
- **String Matching:** 997162 ms ~ 16 minutes
- **Indexed:** 1760917 ms ~ 29 minutes
- **RegEx:** 1835185 ms ~ 31 minutes

I was pretty surprised by the results. My initial thought would have been that indexing would be <br />
the fastest. I think my results showed simple string matching as the fastest for a couple of reasons: <br />
1. String matching method takes in an entire line rather than each word. I tested this theory by changing <br />
the method to scan word by word rather than line by line. The results of this test showed this method to be <br />
dramatically slower at 4300248 ms ~ 72 minutes. My decision to scan line by line is to account for searching multiple <br />
word phrases. 
2. I think that Indexing would be faster had I implemented it a little differently. Because the <br />
method loads a new hashMap each time it is called, this takes O(n) time, even though looking up the word in the <br />
hashmap is constant time. I think a better approach for this amount of searches would most likely using a data store <br />
so that the hashmaps would not have to be created each time a user wanted to search a word. 

##Thoughts on Scaling Program
In order for this program to handle massive content and/or very large request volume, some significant changes would need to <br />
be made. As I mentioned above, using a data store to store the searched documents. I also spent some time researching how massive <br />
search engines work (like Google) and discovered a Java search engine library called Lucene. <br /><br />

