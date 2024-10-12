# MTD (Multi-Threaded DoS Attack Tool)

## Description

MTD is a simple multi-threaded DoS (Denial of Service) attack tool written in Java. It allows users to send multiple HTTP POST requests to specified domains, optionally utilizing a list of proxy servers to anonymize the requests. The tool is primarily intended for educational purposes, to demonstrate the concept of DoS attacks and the importance of network security.

## Features

- **Multi-Threaded**: Sends multiple requests concurrently to improve attack efficiency.
- **Proxy Support**: Option to route requests through proxy servers for anonymity.
- **Domain Check**: Validates the availability of target domains before starting the attack.
- **Customizable Request Parameters**: Allows users to define data size, quantity of requests, and delay between requests.
- **Real-Time Feedback**: Provides real-time updates on request statuses and response times.

## Prerequisites

- Java Development Kit (JDK) 8 or higher installed on your machine.
- Basic understanding of networking concepts.

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/MEMozki/MTD.git
   cd MTD/3.0
   ```

2. Compile the Java file:

   ```bash
   javac mtd.java
   ```

## Usage

1. Run the compiled Java program:

   ```bash
   java DoSAttack
   ```

2. Follow the on-screen prompts to:

   - Enter the target domains (comma-separated).
   - Specify the size of the data to be sent.
   - Choose the quantity of requests.
   - Set the delay between requests (in seconds).
   - Decide whether to use proxy servers.

3. The program will validate the domains and start sending requests based on your input.

## Important Notes

- This tool is intended for educational and research purposes only. Unauthorized use against servers or networks without permission is illegal and unethical.
- Ensure you have explicit permission to test any network or system you target with this tool.
- Use responsibly and understand the legal implications of network testing.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Disclaimer

The author and contributors of this tool do not take any responsibility for misuse or damages caused by the use of this tool.
