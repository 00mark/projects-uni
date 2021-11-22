#include <stdio.h>
#include <gsl/gsl_randist.h>

/**
 * Prints the values of gsl_ran_gaussian_pdf() from start to end with step size step
 * @param start: double, start value
 * @param end: double, end value
 * @param step: double, stepvalue
 */
void print_vals(double start, double end, double step)
{
    double cur;

    for(cur = start; cur <= end; cur += step){
        printf("%f %f\n", cur, gsl_ran_gaussian_pdf(cur, 1));
    }
}

int main()
{
    print_vals(-5.0, 5.0, 0.1);

    return 0;
}
