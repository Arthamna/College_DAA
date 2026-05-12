#include <bits/stdc++.h>
using namespace std;

int main() {
    int b;
    scanf("%d", &b);
    for (int r = 1; r <= b; ++r) {
        int s;
        scanf("%d", &s);
        int n = s - 1;
        
        long long best_sum = LLONG_MIN;
        int best_l = -1, best_r = -1, best_len = 0;
        long long cur_sum = 0;
        int cur_start = 1;
        
        for (int i = 1; i <= n; ++i) {
            int x;
            scanf("%d", &x);
            cur_sum += x;
            
            int cur_len = i - cur_start + 1;
            if (cur_sum > best_sum || (cur_sum == best_sum && cur_len > best_len)) {
                best_sum = cur_sum;
                best_l = cur_start;
                best_r = i;
                best_len = cur_len;
            }
            
            if (cur_sum < 0) {
                cur_sum = 0;
                cur_start = i + 1;
            }
        }
        
        if (best_sum > 0) { printf("The nicest part of route %d is between stops %d and %d\n", r, best_l, best_r + 1);} 
        else { printf("Route %d has no nice parts\n", r); }
    }
    return 0;
}