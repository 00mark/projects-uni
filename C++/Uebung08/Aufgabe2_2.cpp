#include <vector>
#include <random>
#include <algorithm>
#include <iterator>
#include <iostream>

using namespace std;

int main()
{
    int i;
    int j;
    int len;
    vector<string> vec(100);
    random_device rb;
    uniform_int_distribution<int> range(33, 126);
    uniform_int_distribution<int> length(5, 10);

    for(i = 0; i < 100; i++)
    {
        len = length(rb);
        string s(" ", len);
        for(j = 0; j < len; j++)
        {
            s[j] = (char)range(rb);
        }
        vec[i] = s;
    }
    sort(vec.begin(), vec.end());
    copy(vec.begin(), vec.end(), ostream_iterator<string>(cout, "\n"));
}
