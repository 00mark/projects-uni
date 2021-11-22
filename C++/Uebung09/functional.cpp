#include <vector>
#include <iostream>
#include <functional>
#include <iterator>
#include <cmath>
#include <algorithm>

using namespace std;
using namespace std::placeholders;

int main(int argc, char** argv)
{

  vector<int> v = {1, 4, 2, 8, 5, 7};

  copy(v.begin(), v.end(), ostream_iterator<int>(cout, " "));
  cout << endl;
  
  /*auto it = remove_if(v.begin(), v.end(),
                      bind(bind(equal_to<int>(),_1, 0),
                                bind(modulus<int>(), _1, 2)));
                                */
  v.erase(remove_if(v.begin(), v.end(),
                      bind(bind(equal_to<int>(),_1, 0),
                                bind(modulus<int>(), _1, 2))), v.end());

  copy(v.begin(), v.end(), ostream_iterator<int>(cout, " "));
  cout << endl;

  return 0;
}
