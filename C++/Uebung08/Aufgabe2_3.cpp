#include <vector>
#include <algorithm>
#include <random>
#include <iostream>

using namespace std;

struct sum_obj
{
    int sum = 0; 
    void operator()(int val)
    {
        sum += val;
        cout << "Summe: " << sum << endl;
    }
};

int main()
{
    int i;
    vector<int> vec(100);
    random_device rd;
    uniform_int_distribution<int> range(1, 100);
    sum_obj so;

    for(i = 0; i < 100; i++)
    {
        vec[i] = range(rd);
    }
    for_each(vec.begin(), vec.end(), so);
}
