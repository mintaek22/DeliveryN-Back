from selenium import webdriver
import time
from bs4 import BeautifulSoup
import pandas as pd

# url입력
driver = webdriver.Chrome('C:/Users/Choco/Desktop/coding/chromedriver_win32/chromedriver.exe')
# 크롬드라이버 경로 설정
url = "https://www.yogiyo.co.kr/"  # 사이트 입력
driver.get(url)  # 사이트 오픈
time.sleep(1)  # 2초 지연

# 검색창 선택
xpath = '''//*[@id="search"]/div/form/input'''  # 검색창
element = driver.find_element_by_xpath(xpath)
element.clear()
time.sleep(1)

# 검색창 입력
element.send_keys("서울특별시 마포구 와우산로 94")

# 조회버튼 클릭
search_xpath = '''//*[@id="button_search_address"]/button[2]'''
driver.find_element_by_xpath(search_xpath).click()
time.sleep(1)

'''
<li ng-repeat="ct in session_storage.categories" class="" ng-hide="! ct.enabled &amp;&amp; ct.optional" ontouchend="(function(){})()" ng-click="select_category(ct, city, zipcode, $event)"><i class="category-icon ico-ct02"></i><span ng-bind="ct.title" class="category-name ng-binding">치킨</span></li>
'''

# 검색 콤보상자 선택
# 선택 : #search > div > form > ul > li:nth-child(3) > a
search_selector = '#search > div > form > ul > li:nth-child(3) > a'
search = driver.find_element_by_css_selector(search_selector)
search.click()
time.sleep(2)

category_list_index = [5, 6, 7, 8, 9, 10, 12]
category_list = ["치킨", "피자/양식", "한식", "중식", "일식/돈까스", "족발/보쌈", "분식"]
result_list = []

for j in range(0, 7):
    category = '#category > ul > li:nth-child(' + str(category_list_index[j]) + ')'
    search_category = driver.find_element_by_css_selector(category)
    search_category.click()
    time.sleep(1)

    SCROLL_PAUSE_SEC = 1

    # 스크롤 높이 가져옴
    last_height = driver.execute_script("return document.body.scrollHeight")

    while True:
        # 끝까지 스크롤 다운
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")

        # 1초 대기
        time.sleep(SCROLL_PAUSE_SEC)

        # 스크롤 다운 후 스크롤 높이 다시 가져옴
        new_height = driver.execute_script("return document.body.scrollHeight")
        if new_height == last_height:
            time.sleep(2)
            last_height = new_height
            if new_height == last_height:
                break
        last_height = new_height

    time.sleep(2)

    # 페이지 소스 출력
    html = driver.page_source
    html_source = BeautifulSoup(html, 'html.parser')

    # 데이터 추출
    restaurant_name = html_source.find_all("div", class_="restaurant-name ng-binding")

    # 데이터 배열
    for i in restaurant_name:
        result_list.append([i.string,category_list[j]])  # 리스트 요소 추가

df = pd.DataFrame(result_list)
df.to_csv("restaurant_name.csv")

driver.close()  # 크롬드라이버 종료
