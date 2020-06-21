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
|  Passthrough HTTP service (h1c -> h1c) | 100 | 50 | 0 | 0 | 15814.57 | 6.04 | 109.77 | 17 | 99.49 | 10.605 |
|  Passthrough HTTP service (h1c -> h1c) | 100 | 1024 | 0 | 0 | 13576.43 | 7.06 | 115.52 | 21 | 99.59 | 11.276 |
|  Passthrough HTTP service (h1c -> h1c) | 300 | 50 | 0 | 0 | 17312.91 | 16.65 | 131.68 | 38 | 98.79 | 14.264 |
|  Passthrough HTTP service (h1c -> h1c) | 300 | 1024 | 0 | 0 | 15831.88 | 18.05 | 145.66 | 41 | 98.82 | 10.618 |
|  Passthrough HTTP service (h1c -> h1c) | 1000 | 50 | 0 | 0 | 15861.75 | 60.13 | 132.89 | 146 | 96.39 | 11.786 |
|  Passthrough HTTP service (h1c -> h1c) | 1000 | 1024 | 0 | 0 | 15302.31 | 62.44 | 120.41 | 151 | 96.44 | 12.874 |
|  JSON to XML transformation HTTP service | 100 | 50 | 0 | 0 | 10103.83 | 9.41 | 121.51 | 29 | 98.81 | 14.671 |
|  JSON to XML transformation HTTP service | 100 | 1024 | 0 | 0 | 6852.11 | 13.95 | 158.83 | 37 | 98.08 | 14.756 |
|  JSON to XML transformation HTTP service | 300 | 50 | 0 | 0 | 10272.69 | 27.81 | 130.34 | 68 | 97.5 | 15.508 |
|  JSON to XML transformation HTTP service | 300 | 1024 | 0 | 0 | 6785.01 | 42.24 | 137.54 | 104 | 95.51 | 16.385 |
|  JSON to XML transformation HTTP service | 1000 | 50 | 0 | 0 | 9615.95 | 99.2 | 116.12 | 254 | 93.57 | 16.509 |
|  JSON to XML transformation HTTP service | 1000 | 1024 | 0 | 0 | 6293.4 | 152 | 171.3 | 415 | 84.63 | 84.393 |
|  Passthrough HTTPS service (h1 -> h1) | 100 | 50 | 0 | 0 | 13813.99 | 6.89 | 80.42 | 19 | 99.48 | 15.326 |
|  Passthrough HTTPS service (h1 -> h1) | 100 | 1024 | 0 | 0 | 10289.6 | 9.25 | 79.31 | 23 | 99.56 | 15.218 |
|  Passthrough HTTPS service (h1 -> h1) | 300 | 50 | 0 | 0 | 14017.91 | 20.36 | 111.42 | 44 | 98.79 | 16.967 |
|  Passthrough HTTPS service (h1 -> h1) | 300 | 1024 | 0 | 0 | 10732.08 | 26.91 | 89.77 | 57 | 98.99 | 16.545 |
|  Passthrough HTTPS service (h1 -> h1) | 1000 | 50 | 0 | 0 | 13220.12 | 73.63 | 113.33 | 151 | 96.76 | 21.573 |
|  Passthrough HTTPS service (h1 -> h1) | 1000 | 1024 | 0 | 0 | 10164.38 | 93.9 | 83.12 | 171 | 97.18 | 19.201 |
|  JSON to XML transformation HTTPS service | 100 | 50 | 0 | 0 | 9213.38 | 10.63 | 75.15 | 31 | 98.92 | 16.107 |
|  JSON to XML transformation HTTPS service | 100 | 1024 | 0 | 0 | 5646.17 | 17.16 | 70.55 | 45 | 98.38 | 16.099 |
|  JSON to XML transformation HTTPS service | 300 | 50 | 0 | 0 | 9305.1 | 30.82 | 89.64 | 71 | 97.84 | 15.882 |
|  JSON to XML transformation HTTPS service | 300 | 1024 | 0 | 0 | 5947.03 | 49.61 | 67.79 | 107 | 96.05 | 16.067 |
|  JSON to XML transformation HTTPS service | 1000 | 50 | 0 | 0 | 8900.44 | 108.68 | 103.12 | 271 | 93.76 | 16.874 |
|  JSON to XML transformation HTTPS service | 1000 | 1024 | 0 | 0 | 4928.03 | 197.01 | 105 | 465 | 86.79 | 148.719 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 100 | 50 | 0 | 0 | 12297.28 | 7.21 | 4.4 | 21 | 99.39 | 15.836 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 100 | 1024 | 0 | 0 | 11870.96 | 7.4 | 4.55 | 22 | 99.41 | 16.014 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 300 | 50 | 0 | 0 | 12861.35 | 21.4 | 8.83 | 47 | 98.75 | 16.269 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 300 | 1024 | 0 | 0 | 12796.64 | 21.12 | 8.87 | 48 | 98.82 | 16.256 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 1000 | 50 | 0 | 0 | 12618.26 | 74.59 | 27.24 | 162 | 96.6 | 17.257 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 1000 | 1024 | 0 | 0 | 12025.23 | 77.59 | 27.24 | 166 | 96.73 | 17.053 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 100 | 50 | 0 | 0 | 11849.32 | 7.68 | 4.4 | 21 | 99.37 | 16.242 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 100 | 1024 | 0 | 0 | 9906.34 | 9.19 | 4.3 | 22 | 99.44 | 16.67 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 300 | 50 | 0 | 0 | 12084.47 | 22.9 | 9.19 | 50 | 98.78 | 17.039 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 300 | 1024 | 0 | 0 | 9921.85 | 28.58 | 11.1 | 61 | 98.96 | 16.231 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 1000 | 50 | 0 | 0 | 11310.15 | 83.31 | 28.02 | 173 | 96.58 | 21.302 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 1000 | 1024 | 0 | 0 | 9849.56 | 95.99 | 29.14 | 181 | 96.83 | 20.376 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 100 | 50 | 0 | 0 | 13806.25 | 6.95 | 89.99 | 20 | 99.55 | 15.923 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 100 | 1024 | 0 | 0 | 12478.39 | 7.65 | 101.51 | 21 | 99.58 | 15.95 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 300 | 50 | 0 | 0 | 14836.8 | 19.43 | 111.09 | 43 | 98.88 | 16.988 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 300 | 1024 | 0 | 0 | 12187.32 | 23.64 | 95.37 | 52 | 99.12 | 16.934 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 1000 | 50 | 0 | 0 | 13768.75 | 69.24 | 120.71 | 148 | 96.95 | 22.572 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 1000 | 1024 | 0 | 0 | 12679.22 | 76.32 | 102.38 | 159 | 97.19 | 18.85 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 100 | 50 | 0 | 0 | 11704.85 | 7.76 | 8.11 | 22 | 99.63 | 15.888 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 100 | 1024 | 0 | 0 | 12014.47 | 7.31 | 4.48 | 21 | 99.64 | 15.819 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 300 | 50 | 0 | 0 | 14138.7 | 19.73 | 8.39 | 44 | 99.12 | 17.958 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 300 | 1024 | 0 | 0 | 13065.47 | 20.65 | 8.67 | 47 | 99.17 | 18.132 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 1000 | 50 | 0 | 0 | 13927.51 | 68.69 | 27.42 | 156 | 97.04 | 20.511 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 1000 | 1024 | 0 | 0 | 11739.04 | 80.21 | 28.5 | 176 | 97.34 | 18.172 |
