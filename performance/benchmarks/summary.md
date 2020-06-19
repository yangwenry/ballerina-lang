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
|  Passthrough HTTP service (h1c -> h1c) | 100 | 50 | 0 | 0 | 16839.86 | 5.73 | 124.25 | 17 | 99.53 | 11.114 |
|  Passthrough HTTP service (h1c -> h1c) | 100 | 1024 | 0 | 0 | 15473.64 | 6.13 | 119.41 | 18 | 99.55 | 11.799 |
|  Passthrough HTTP service (h1c -> h1c) | 300 | 50 | 0 | 0 | 17644.45 | 16.16 | 172.86 | 37 | 98.71 | 16.244 |
|  Passthrough HTTP service (h1c -> h1c) | 300 | 1024 | 0 | 0 | 16867.81 | 16.97 | 195.77 | 38 | 98.85 | 14.275 |
|  Passthrough HTTP service (h1c -> h1c) | 1000 | 50 | 0 | 0 | 14602.24 | 65.59 | 114.51 | 154 | 96.64 | 15.561 |
|  Passthrough HTTP service (h1c -> h1c) | 1000 | 1024 | 0 | 0 | 15775.22 | 60.34 | 119.73 | 151 | 96.41 | 12.67 |
|  JSON to XML transformation HTTP service | 100 | 50 | 0 | 0 | 10698.13 | 8.89 | 97.64 | 27 | 98.74 | 14.671 |
|  JSON to XML transformation HTTP service | 100 | 1024 | 0 | 0 | 7070.45 | 13.53 | 161.51 | 36 | 98.01 | 14.688 |
|  JSON to XML transformation HTTP service | 300 | 50 | 0 | 0 | 11214.87 | 26.47 | 97.05 | 65 | 97.52 | 15.722 |
|  JSON to XML transformation HTTP service | 300 | 1024 | 0 | 0 | 7190.55 | 39.76 | 160.48 | 99 | 95.3 | 16.062 |
|  JSON to XML transformation HTTP service | 1000 | 50 | 0 | 0 | 10256.68 | 94.04 | 119.23 | 251 | 93.3 | 16.186 |
|  JSON to XML transformation HTTP service | 1000 | 1024 | 0 | 0 | 6418.49 | 149.02 | 148.16 | 411 | 84.81 | 85.463 |
|  Passthrough HTTPS service (h1 -> h1) | 100 | 50 | 0 | 0 | 12949.67 | 7.32 | 89.35 | 22 | 99.54 | 15.339 |
|  Passthrough HTTPS service (h1 -> h1) | 100 | 1024 | 0 | 0 | 10699.92 | 8.94 | 78.8 | 21 | 99.53 | 15.204 |
|  Passthrough HTTPS service (h1 -> h1) | 300 | 50 | 0 | 0 | 14769.65 | 19.36 | 109.97 | 42 | 98.74 | 16.169 |
|  Passthrough HTTPS service (h1 -> h1) | 300 | 1024 | 0 | 0 | 11477.32 | 26.08 | 82.21 | 54 | 99.04 | 16.177 |
|  Passthrough HTTPS service (h1 -> h1) | 1000 | 50 | 0 | 0 | 13503.48 | 70.94 | 114.55 | 150 | 96.66 | 20.614 |
|  Passthrough HTTPS service (h1 -> h1) | 1000 | 1024 | 0 | 0 | 10562.74 | 92.26 | 84.19 | 170 | 97.18 | 20.161 |
|  JSON to XML transformation HTTPS service | 100 | 50 | 0 | 0 | 9416.4 | 10.17 | 87.79 | 30 | 98.92 | 16.088 |
|  JSON to XML transformation HTTPS service | 100 | 1024 | 0 | 0 | 5856.44 | 16.64 | 63.9 | 44 | 98.36 | 16.163 |
|  JSON to XML transformation HTTPS service | 300 | 50 | 0 | 0 | 9680.03 | 29.5 | 98.83 | 69 | 97.76 | 16.364 |
|  JSON to XML transformation HTTPS service | 300 | 1024 | 0 | 0 | 6050.16 | 48.78 | 81.89 | 105 | 96.1 | 15.898 |
|  JSON to XML transformation HTTPS service | 1000 | 50 | 0 | 0 | 9387.77 | 103.96 | 103.28 | 263 | 93.58 | 17.812 |
|  JSON to XML transformation HTTPS service | 1000 | 1024 | 0 | 0 | 5208.17 | 183.5 | 102.61 | 419 | 86.49 | 144.106 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 100 | 50 | 0 | 0 | 13318.88 | 6.68 | 4.32 | 20 | 99.36 | 16.03 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 100 | 1024 | 0 | 0 | 12383.34 | 6.99 | 4.34 | 21 | 99.51 | 15.805 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 300 | 50 | 0 | 0 | 13508.92 | 20.13 | 8.58 | 45 | 98.77 | 16.315 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 300 | 1024 | 0 | 0 | 13173.67 | 20.4 | 8.74 | 47 | 98.79 | 16.251 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 1000 | 50 | 0 | 0 | 11936.23 | 78.42 | 28.3 | 170 | 96.86 | 17.289 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1c) | 1000 | 1024 | 0 | 0 | 11753.56 | 79.48 | 28.69 | 172 | 96.89 | 17.291 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 100 | 50 | 0 | 0 | 12500.69 | 7.34 | 4.26 | 20 | 99.36 | 15.808 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 100 | 1024 | 0 | 0 | 10406.36 | 8.82 | 4.18 | 21 | 99.47 | 15.88 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 300 | 50 | 0 | 0 | 12590.87 | 22.19 | 9.23 | 49 | 98.63 | 16.248 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 300 | 1024 | 0 | 0 | 10653.42 | 26.57 | 10.18 | 56 | 98.89 | 16.552 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 1000 | 50 | 0 | 0 | 11664.46 | 80.92 | 27.01 | 167 | 96.58 | 19.909 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h1) | 1000 | 1024 | 0 | 0 | 9621.47 | 99.02 | 30.7 | 189 | 97.02 | 18.606 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 100 | 50 | 0 | 0 | 13345.04 | 7.17 | 80.77 | 21 | 99.57 | 15.949 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 100 | 1024 | 0 | 0 | 13584.95 | 7.24 | 102.56 | 21 | 99.59 | 15.934 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 300 | 50 | 0 | 0 | 15182.66 | 18.78 | 116.29 | 42 | 98.94 | 9341 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 300 | 1024 | 0 | 0 | 13871.67 | 20.63 | 123.55 | 45 | 98.99 | 16.515 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 1000 | 50 | 0 | 0 | 14059.7 | 67.89 | 117.42 | 147 | 96.95 | 22.502 |
|  HTTP/2 client and server downgrade service (h2 -> h2) | 1000 | 1024 | 0 | 0 | 12309.5 | 77.85 | 100.72 | 158 | 97.34 | 22.803 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 100 | 50 | 0 | 0 | 13134.83 | 6.65 | 4.28 | 20 | 99.6 | 15.886 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 100 | 1024 | 0 | 0 | 12499.39 | 6.88 | 4.36 | 21 | 99.63 | 15.897 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 300 | 50 | 0 | 0 | 14315.64 | 18.92 | 8.3 | 43 | 99.1 | 18.323 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 300 | 1024 | 0 | 0 | 13473.96 | 19.84 | 8.6 | 46 | 99.14 | 18.335 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 1000 | 50 | 0 | 0 | 12920.16 | 73.12 | 27.19 | 166 | 97.14 | 18.324 |
|  Passthrough HTTP/2(over TLS) service (h2 -> h2) | 1000 | 1024 | 0 | 0 | 12358.77 | 76.88 | 30.38 | 177 | 97.29 | 20.473 |
