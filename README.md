# MTD

**Website Stress Testing Tool** is a Python-based tool designed to simulate multiple HTTP requests to a website, primarily for testing server performance under heavy load. **This tool should only be used for ethical purposes, with permission from the website owner.**

## Features

- **Multi-threaded Requesting**: The tool uses multiple threads to send requests in parallel.
- **Custom Data Payload**: Users can define the size of the data sent in each request.
- **Adjustable Request Rate**: Users can control the delay between requests, simulating different traffic loads.
- **Real-Time Response Logging**: Displays request success, status codes, and data sizes in real time.
- **Clear and Simple CLI**: Provides a straightforward command-line interface for ease of use.

### Important Note

This tool should **only** be used on websites for which you have explicit permission. Unauthorized use on other websites is **illegal** and could lead to legal consequences.

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/memozki/mtd.git
   ```

2. **Navigate to the project directory:**

   ```bash
   cd stress-testing-tool
   ```

3. **Install the required dependencies:**

   ```bash
   pip install -r requirements.txt
   ```

## Usage

1. **Run the tool:**

   To start the stress test, simply run the script:
   
   ```bash
   python stress_test.py
   ```

2. **Input parameters:**

   The script will prompt you for several inputs:
   
   - `HOST`: The domain of the website you want to test (e.g., `example.com`).
   - `DATASIZE`: Size of the data (in bytes) to send with each request.
   - `QUANTITY`: Number of requests to send.
   - `DELAY`: Time delay (in seconds) between each request.

3. **Example:**

   ```bash
   HOST>> example.com
   DATASIZE>> 100
   QUANTITY>> 500
   DELAY>> 0.1
   ```

   The script will then start sending requests and display the response information in real time.

## Dependencies

- `requests`: Handles the HTTP requests.
- `threading`: Provides multi-threading capabilities for simultaneous requests.
- `colorama`: Adds color to the command-line interface for better readability.

You can install the dependencies by running:

```bash
pip install requests colorama
```

## Legal & Ethical Use

This tool is for **educational purposes only**. Do not use it to disrupt, harm, or overload websites without the proper authorization. Misuse of this tool may result in legal consequences. Always ensure you have permission before running tests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Let me know if you'd like to adjust any details or further clarify the usage!
