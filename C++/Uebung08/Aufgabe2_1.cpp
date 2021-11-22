#include <vector>
#include <random>
#include <iostream>
#include <iterator>

using namespace std;

int main()
{
    int i;
    vector<int> vec(100);
    random_device rd;
    uniform_int_distribution<int> range(1, 100);

    for(i = 0; i < 100; i++)
    {
        vec[i] = range(rd);
    }
    copy(vec.begin(), vec.end(), ostream_iterator<int>(cout, "\n"));
}
