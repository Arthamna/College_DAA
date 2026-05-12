#include <bits/stdc++.h>
using namespace std;

int main() {
    int n;
    while (scanf("%d", &n) && n != 0) {
        vector<long long> h(n);
        for (int i = 0; i < n; ++i) {
            scanf("%lld", &h[i]);
        }
        stack<int> st;
        st.push(-1);
        long long ans = 0;

        for (int i = 0; i <= n; ++i) {
            long long cur = (i == n) ? 0 : h[i];
            while (st.top() != -1 && h[st.top()] >= cur) {
                long long height = h[st.top()]; 
                st.pop();
                long long width  = i - st.top() - 1;
                ans = max(ans, height * width);
            }
            st.push(i);
        }

        printf("%lld\n", ans);
    }
    return 0;
}