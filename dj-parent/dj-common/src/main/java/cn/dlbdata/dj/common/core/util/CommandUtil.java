package cn.dlbdata.dj.common.core.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.InputStreamReader;

public class CommandUtil {

	static String SYS_CHARSET_NAME = "UTF-8";
	static {
		SYS_CHARSET_NAME = System.getProperty("file.encoding");
		SYS_CHARSET_NAME = System.getProperty("sun.jnu.encoding");
		System.out.println(SYS_CHARSET_NAME + ":" + System.getProperty("file.encoding"));
	}

	/**
	 * 执行系统命令, 返回执行结果
	 *
	 * @param cmd
	 *            需要执行的命令
	 * @param dir
	 *            执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
	 */
	public static String execCmd(String cmd, File dir) throws Exception {
		StringBuilder result = new StringBuilder();

		Process process = null;
		BufferedReader bufrIn = null;
		BufferedReader bufrError = null;
		
		try {
			// 执行命令, 返回一个子进程对象（命令在子进程中执行）
			process = Runtime.getRuntime().exec(cmd, null, dir);

			// 方法阻塞, 等待命令执行完成（成功会返回0）
			process.waitFor();

			// 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
			bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), SYS_CHARSET_NAME));
			bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), SYS_CHARSET_NAME));

			// 读取输出
			String line = null;
			while ((line = bufrIn.readLine()) != null) {
				result.append(line).append("\n");
			}
			while ((line = bufrError.readLine()) != null) {
				result.append(line).append("\n");
			}

		} finally {
			closeStream(bufrIn);
			closeStream(bufrError);

			// 销毁子进程
			if (process != null) {
				process.destroy();
			}
		}

		// 返回执行结果
		// return new String(result.toString().getBytes("utf-8"),"gbk");
		return result.toString();
	}

	private static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (Exception e) {
				// nothing
			}
		}
	}

	public static void main(String[] args) {
		try {
			// CommandLine cmdLine = new CommandLine("java -version");
			// DefaultExecutor executor = new DefaultExecutor();
			//
			// //设置命令执行退出值为1，如果命令成功执行并且没有错误，则返回1
			// executor.setExitValue(1);
			// int exitValue = executor.execute(cmdLine);
			// System.out.println(exitValue);
			// System.getProperties().list(System.out);
			String result = execCmd("ping 192.168.1.1", null);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
