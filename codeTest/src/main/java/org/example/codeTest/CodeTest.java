package org.example.codeTest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class CodeTest {

    private void learningList() {
        List<Integer> node = Lists.newArrayList();

        node.add(3);

        log.info("node = {}", JSON.toJSONString(node));
    }

    public static void main(String[] args) {
        CodeTest codeTest = new CodeTest();
        codeTest.learningList();
    }
}
