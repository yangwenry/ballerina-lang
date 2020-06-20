# Ballerina Performance Test Results

During each release, we execute various automated performance test scenarios and publish the results.

| Test Scenarios | Description |
| --- | --- |
| Passthrough HTTP service (h1c -> h1c) | An HTTP Service, which forwards all requests to an HTTP back-end service. |
| Passthrough HTTPS service (h1 -> h1) | An HTTPS Service, which forwards all requests to an HTTPS back-end service. |
| JSON to XML transformation HTTP service | An HTTP Service, which transforms JSON requests to XML and then forwards all requests to an HTTP back-end service. |
| JSON to XML transformation HTTPS service | An HTTPS Service, which transforms JSON requests to XML and then forwards all requests to an HTTPS back-end service. |
| Passthrough HTTP/2(over TLS) service (h2 -> h2) | An HTTPS Service exposed over HTTP/2 protocol, which forwards all requests to an HTTP/2(over TLS) back-end service. |
| Passthrough HTTP/2(over TLS) service (h2 -> h1) | An HTTPS Service exposed over HTTP/2 protocol, which forwards all requests to an HTTPS back-end service. |
| Passthrough HTTP/2(over TLS) service (h2 -> h1c) | An HTTPS Service exposed over HTTP/2 protocol, which forwards all requests to an HTTP back-end service. |
| HTTP/2 client and server downgrade service (h2 -> h2) | An HTTP/2(with TLS) server accepts requests from an HTTP/1.1(with TLS) client and the HTTP/2(with TLS) client sends requests to an HTTP/1.1(with TLS) back-end service. Both the upstream and the downgrade connection is downgraded to HTTP/1.1(with TLS). |

Our test client is [Apache JMeter](https://jmeter.apache.org/index.html). We test each scenario for a fixed duration of
time. We split the test results into warmup and measurement parts and use the measurement part to compute the
performance metrics.

A majority of test scenarios use a [Netty](https://netty.io/) based back-end service which echoes back any request
posted to it after a specified period of time.

We run the performance tests under different numbers of concurrent users, message sizes (payloads) and back-end service
delays.

The main performance metrics:

1. **Throughput**: The number of requests that the Ballerina service processes during a specific time interval (e.g. per second).
2. **Response Time**: The end-to-end latency for an operation of invoking a Ballerina service. The complete distribution of response times was recorded.

In addition to the above metrics, we measure the load average and several memory-related metrics.

The following are the test parameters.

| Test Parameter | Description | Values |
| --- | --- | --- |
| Scenario Name | The name of the test scenario. | Refer to the above table. |
| Heap Size | The amount of memory allocated to the application | 2G |
| Concurrent Users | The number of users accessing the application at the same time. | 100, 300, 1000 |
| Message Size (Bytes) | The request payload size in Bytes. | 50, 1024 |
| Back-end Delay (ms) | The delay added by the back-end service. | 0 |

The duration of each test is **900 seconds**. The warm-up period is **300 seconds**.
The measurement results are collected after the warm-up period.

A [**c5.xlarge** Amazon EC2 instance](https://aws.amazon.com/ec2/instance-types/) was used to install Ballerina.

The following are the measurements collected from each performance test conducted for a given combination of
test parameters.

| Measurement | Description |
| --- | --- |
| Error % | Percentage of requests with errors |
| Average Response Time (ms) | The average response time of a set of results |
| Standard Deviation of Response Time (ms) | The “Standard Deviation” of the response time. |
| 99th Percentile of Response Time (ms) | 99% of the requests took no more than this time. The remaining samples took at least as long as this |
| Throughput (Requests/sec) | The throughput measured in requests per second. |
| Average Memory Footprint After Full GC (M) | The average memory consumed by the application after a full garbage collection event. |

The following is the summary of performance test results collected for the measurement period.

|  Scenario Name | Concurrent Users | Message Size (Bytes) | Back-end Service Delay (ms) | Error % | Throughput (Requests/sec) | Average Response Time (ms) | Standard Deviation of Response Time (ms) | 99th Percentile of Response Time (ms) | Ballerina GC Throughput (%) | Average Ballerina Memory Footprint After Full GC (M) |
|---|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|
|  Passthrough HTTP service (h1c -> h1c) | 100 | 50 | 0 | 0 | 17409.16 | 5.44 | 109.56 | 16 | 99.53 |  |
|  Passthrough HTTP service (h1c -> h1c) | 100 | 1024 | 0 | 0 | 16496.79 | 5.78 | 105.46 | 17 | 99.56 |  |
|  Passthrough HTTP service (h1c -> h1c) | 300 | 50 | 0 | 0 | 19141.13 | 14.99 | 181.85 | 34 | 98.81 |  |
|  Passthrough HTTP service (h1c -> h1c) | 300 | 1024 | 0 | 0 | 17956.29 | 15.94 | 120.85 | 37 | 98.82 |  |
|  Passthrough HTTP service (h1c -> h1c) | 1000 | 50 | 0 | 0 | 18117.53 | 53.07 | 120.85 | 130 | 96.26 | 20.079 |
|  Passthrough HTTP service (h1c -> h1c) | 1000 | 1024 | 0 | 0 | 17462.12 | 54.52 | 116.66 | 134 | 96.28 | 24.455 |
|  JSON to XML transformation HTTP service | 100 | 50 | 0 | 0 | 12702.86 | 7.49 | 99.34 | 23 | 98.86 | 15.008 |
|  JSON to XML transformation HTTP service | 100 | 1024 | 0 | 0 | 8253.38 | 11.51 | 126.4 | 32 | 98.27 | 15.107 |
|  JSON to XML transformation HTTP service | 300 | 50 | 0 | 0 | 13238.45 | 22.04 | 100.87 | 58 | 97.59 | 15.572 |
|  JSON to XML transformation HTTP service | 300 | 1024 | 0 | 0 | 7758.84 | 37.07 | 123.71 | 95 | 95.59 | 8938 |
|  JSON to XML transformation HTTP service | 1000 | 50 | 0 | 0 | 12597.62 | 79.32 | 101.86 | 236 | 92.77 | 16.21 |
|  JSON to XML transformation HTTP service | 1000 | 1024 | 0 | 0 | 6901.9 | 139.32 | 132.3 | 387 | 84.65 | 83.401 |
|  Passthrough HTTPS service (h1 -> h1) | 100 | 50 | 0 | 0 | 15181.91 | 6.27 | 84.43 | 17 | 99.51 | 15.444 |
|  Passthrough HTTPS service (h1 -> h1) | 100 | 1024 | 0 | 0 | 11198.14 | 8.49 | 88.42 | 20 | 99.57 | 15.53 |
|  Passthrough HTTPS service (h1 -> h1) | 300 | 50 | 0 | 0 | 16144.7 | 17.88 | 94.55 | 39 | 98.84 | 16.663 |
|  Passthrough HTTPS service (h1 -> h1) | 300 | 1024 | 0 | 0 | 11669.83 | 24.44 | 83.3 | 51 | 99.02 | 16.931 |
|  Passthrough HTTPS service (h1 -> h1) | 1000 | 50 | 0 | 0 | 14645.82 | 64.97 | 100.21 | 136 | 96.62 | 19.703 |
|  Passthrough HTTPS service (h1 -> h1) | 1000 | 1024 | 0 | 0 | 11167.35 | 85.65 | 78.41 | 158 | 97.12 | 20.815 |
|  JSON to XML transformation HTTPS service | 100 | 50 | 0 | 0 | 10025.92 | 9.5 | 87.29 | 28 | 99.03 | 16.065 |
|  JSON to XML transformation HTTPS service | 100 | 1024 | 0 | 0 | 6518.23 | 14.75 | 76.31 | 38 | 98.47 | 16.13 |
|  JSON to XML transformation HTTPS service | 300 | 50 | 0 | 0 | 10595.91 | 27.11 | 89.54 | 64 | 97.89 | 16.684 |
|  JSON to XML transformation HTTPS service | 300 | 1024 | 0 | 0 | 6176.83 | 46.87 | 80.22 | 102 | 96.3 | 17.632 |
|  JSON to XML transformation HTTPS service | 1000 | 50 | 0 | 0 | 9774.34 | 97.75 | 96.28 | 245 | 93.75 | 20.137 |
|  JSON to XML transformation HTTPS service | 1000 | 1024 | 0 | 0 | 5875.12 | 170.15 | 114.99 | 439 | 86.19 | 155.929 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 100 | 50 | 0 | 0 | 14096.92 | 6.4 | 3.83 | 18 | 99.36 | 16.082 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 100 | 1024 | 0 | 0 | 13701.46 | 6.52 | 3.73 | 18 | 99.41 | 16.069 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 300 | 50 | 0 | 0 | 14819.46 | 18.58 | 8.03 | 42 | 98.75 | 16.419 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 300 | 1024 | 0 | 0 | 14024.31 | 19.1 | 8.06 | 43 | 98.81 | 16.606 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 1000 | 50 | 0 | 0 | 13692.32 | 68.72 | 24.56 | 151 | 96.55 | 17.484 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 1000 | 1024 | 0 | 0 | 13425.01 | 69.92 | 26.66 | 158 | 96.6 | 17.21 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 100 | 50 | 0 | 0 | 12822.91 | 6.98 | 3.77 | 18 | 99.38 | 15.807 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 100 | 1024 | 0 | 0 | 10769.04 | 8.33 | 3.81 | 20 | 99.45 | 15.697 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 300 | 50 | 0 | 0 | 13329.65 | 20.61 | 8.44 | 45 | 98.75 | 17.504 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 300 | 1024 | 0 | 0 | 11434.92 | 24.56 | 9.37 | 52 | 98.89 | 17.194 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 1000 | 50 | 0 | 0 | 12547.9 | 76.07 | 25.9 | 158 | 96.58 | 19.981 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 1000 | 1024 | 0 | 0 | 10913.85 | 89.57 | 27.86 | 172 | 96.9 | 22.576 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 100 | 50 | 0 | 0 | 15645.49 | 6.11 | 93.76 | 17 | 99.56 | 16.193 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 100 | 1024 | 0 | 0 | 13895.45 | 6.82 | 95.81 | 18 | 99.59 | 16.279 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 300 | 50 | 0 | 0 | 16532.98 | 17.31 | 107.27 | 38 | 98.86 | 16.667 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 300 | 1024 | 0 | 0 | 14835.79 | 19.4 | 111.44 | 42 | 99.02 | 17.082 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 1000 | 50 | 0 | 0 | 15010.36 | 63.49 | 107.35 | 135 | 96.93 | 19.352 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 1000 | 1024 | 0 | 0 | 13819.92 | 69.3 | 103.97 | 142 | 97.19 | 22.673 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 100 | 50 | 0 | 0 | 14243.31 | 6.31 | 3.85 | 18 | 99.6 | 15.955 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 100 | 1024 | 0 | 0 | 13228.77 | 6.59 | 3.91 | 19 | 99.63 | 15.957 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 300 | 50 | 0 | 0 | 15712.11 | 17.5 | 7.69 | 40 | 99.06 | 16.829 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 300 | 1024 | 0 | 0 | 14445.5 | 18.47 | 7.97 | 42 | 99.14 | 17.723 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 1000 | 50 | 0 | 0 | 15290.83 | 61.66 | 24.61 | 143 | 96.86 | 19.903 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 1000 | 1024 | 0 | 0 | 14166.4 | 65.02 | 25.44 | 152 | 97.06 | 22.644 |
