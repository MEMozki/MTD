```markdown
# MTD - DoS Attack Script (Version 3.0)

## Overview

This project is a basic **Denial of Service (DoS)** attack simulation script written in **Java**. It performs a high volume of HTTP POST requests to a given target host using either direct connections or through random proxies.

> **Disclaimer**: This tool is for educational purposes only. Performing unauthorized attacks on networks or servers is illegal and unethical. Always ensure you have permission from the server owner before testing.

## Features

- **Multiple Hosts**: Attack multiple hosts simultaneously by specifying them in a comma-separated list.
- **Custom Data Size**: Specify the size of the data payload in each request.
- **Request Quantity**: Define the number of requests per host.
- **Request Rate**: Control the rate of requests per second.
- **Proxy Support**: Optionally route requests through a predefined list of proxies to obfuscate origin.
- **Reachability Check**: Validates if hosts are reachable before launching the attack.
- **Threading**: Utilizes multithreading (via Java's `ExecutorService`) to send requests in parallel.

## Requirements

- **Java 8 or later**

## Usage

1. Clone the repository:
   ```bash
   git clone https://github.com/MEMozki/MTD.git
   ```

2. Compile the Java program:
   ```bash
   javac mtd.java
   ```

3. Run the compiled program:
   ```bash
   java DoSAttack
   ```

### Parameters during execution

1. **Hosts**: Enter a comma-separated list of hostnames to target.
   - Example: `example.com, test.com`
   
2. **Data Size**: Enter the size of the data payload in bytes for each request.
   - Example: `1024`
   
3. **Quantity**: Enter the number of requests per host.
   - Example: `1000`
   
4. **Delay**: Enter the number of requests per second.
   - Example: `10`
   
5. **Use Proxy**: Choose whether to route traffic through a proxy by typing `yes` or `no`.

## Proxy Support

The script includes a small list of HTTP proxies. The proxy list can be expanded by modifying the `proxies` array in the `DoSAttack` class. If proxy usage is enabled, a random proxy from the list will be selected for each request.

## Example Output

```bash
(1) | example.com - 200 : 0.25 ms | 1024 : 512 BYTE | 10.10.1.10:3128 | 12:34:56.789
(2) | test.com - 0 : fail | 0 : 0 BYTE | None | 12:34:56.999
```

## Contributing

Feel free to contribute by opening issues or submitting pull requests to improve the functionality or add features.

## License

This project is licensed under the MIT License.
```
