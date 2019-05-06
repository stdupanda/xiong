
public class JsonFormatTool {
    /**
     * 格式化
     *
     * @param jsonStr
     * @return
     */
    private static String LF = System.getProperty("line.separator");

    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr))
            return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);

            // 遇到{ [换行，且下一行缩进
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append(LF);
                    indent++;
                    addIndentBlank(sb, indent);
                    break;

                // 遇到} ]换行，当前行缩进
                case '}':
                case ']':
                    sb.append(LF);
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;

                // 遇到,换行
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append(LF);
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            // sb.append('\t');
            sb.append("  ");
        }
    }

    public static void main(String[] args) {
        String aa = "{ \"head\": { \"cmd\":\"00030003\", \"version\":\"1.0.1\", \"time\":\"2015/07/02 16:15:14\", \"imei\":\"\", \"salt\":\"\", \"algversion\":\"\", \"handshake\":\"\" }, \"data\": { \"userid\":\"\", \"cardno\":\"\", \"tradeno\":\"\", \"money\":\"\" } } ";
        System.out.println(formatJson(aa));
    }
}
