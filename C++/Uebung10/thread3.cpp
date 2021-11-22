#include <iostream>
#include <vector>
#include <thread>
#include <future>
using namespace std;

int square(int x)
{
    return x * x;
}

int main() 
{
    int square_sum = 0;
    vector<future<int>> tasks;

    for (int i = 1; i <= 20; i++)
    {
        tasks.push_back(async(&square, i));
    }
    for(auto& val : tasks)
    {
        square_sum += val.get();
    }

    cout << "Sum of squares up to 20 is = " << square_sum << endl;
    return 0;
}
