

import java.io.File;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.DirUsage;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NfsFileSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarLoader;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarProxyCache;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.util.PrintfFormat;

import com.dengqiang.util.MD5Util;

public class SystemInfo {
	private Sigar sigar ;
	   
    private SigarProxy proxy;
   
    private StringBuilder info = new StringBuilder();

    private void sigarInit(boolean isProxy) {
        sigar = new Sigar();
        if(isProxy)
            proxy = SigarProxyCache.newInstance(this.sigar);
    }
   
    private void shutdown() {
        this.sigar.close();
    }
   

   
    public String getInfo() {
        return info.toString();
    }

    public void clearInfo() {
        if ( null != info )
            info.delete(0,info.length());
    }

    private void println(String arg){
        info.append(arg+"\n");
    }
   
    public String sprintf(String format, Object[] items) {
        return new PrintfFormat(format).sprintf(items);
    }

    public void printf(String format, Object[] items) {
        println(sprintf(format, items));
    }
    
    public void cpuInfo() {
        clearInfo();
        println("============Current system Cpu information================");

        try {
           
            sigarInit(false);
           
            org.hyperic.sigar.CpuInfo[] infos =
                this.sigar.getCpuInfoList();

            CpuPerc[] cpus =
                this.sigar.getCpuPercList();

            org.hyperic.sigar.CpuInfo info = infos[0];
            long cacheSize = info.getCacheSize();
            println("Vendor........." + info.getVendor());
            println("Model.........." + info.getModel());
            println("Mhz............" + info.getMhz());
            println("Total CPUs....." + info.getTotalCores());
            println("Physical CPUs.." + info.getTotalSockets());
            println("Cores per CPU.." + info.getCoresPerSocket());


            if (cacheSize != Sigar.FIELD_NOTIMPL) {
                println("Cache size...." + cacheSize);
            }
            println("");

            for (int i=0; i<cpus.length; i++) {
                println("CPU " + i + ".........");
                outputCpuPerc(cpus[i]);
            }

            println("Totals........");
            outputCpuPerc(this.sigar.getCpuPerc());
        } catch (SigarException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            shutdown();
        }
    }   

    private void outputCpuPerc(CpuPerc cpu) {
        println("User Time....." + CpuPerc.format(cpu.getUser()));
        println("Sys Time......" + CpuPerc.format(cpu.getSys()));
        println("Idle Time....." + CpuPerc.format(cpu.getIdle()));
        println("Wait Time....." + CpuPerc.format(cpu.getWait()));
        println("Nice Time....." + CpuPerc.format(cpu.getNice()));
        println("Combined......" + CpuPerc.format(cpu.getCombined()));
        println("Irq Time......" + CpuPerc.format(cpu.getIrq()));
        if (SigarLoader.IS_LINUX) {
            println("SoftIrq Time.." + CpuPerc.format(cpu.getSoftIrq()));
            println("Stolen Time...." + CpuPerc.format(cpu.getStolen()));
        }
        println("");
    }
    private static Long format(long value) {
        return new Long(value / 1024);
    }
   
    public void memInfo() throws SigarException {
        clearInfo();
        println("============Display information about free and used memory================");
       
        try {
            sigarInit(false);
           
            Mem mem   = this.sigar.getMem();
            Swap swap = this.sigar.getSwap();

            Object[] header = new Object[] { "total", "used", "free" };
           System.out.println(header);
            Object[] memRow = new Object[] {
                format(mem.getTotal()),
                format(mem.getUsed()),
                format(mem.getFree())
            };
           System.out.println(memRow);
            Object[] memPercent = new Object[3];
            memPercent[0] = "100%";
            double   d   =   mem.getUsedPercent(); 
            d=((int)(d*100))/100;         // 取小数点后两位的简便方法
            memPercent[1] = d + "%";
            d   =   mem.getFreePercent(); 
            d=((int)(d*100))/100;
            memPercent[2] = d + "%";      // 取小数点后两位的简便方法
               System.out.println(memPercent);
            //e.g. linux
            Object[] actualRow = new Object[] {
                format(mem.getActualUsed()),
                format(mem.getActualFree())
            };
            if ((mem.getUsed() != mem.getActualUsed()) ||
                (mem.getFree() != mem.getActualFree()))
            {
                System.out.println(actualRow);
            }

            Object[] swapRow = new Object[] {
                format(swap.getTotal()),
                format(swap.getUsed()),
                format(swap.getFree())
            };
           System.out.println(swapRow);
           System.out.println(new Object[] { mem.getRam()});

        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            shutdown();
        }
    }
   public void dfInfo() throws SigarException {
           clearInfo();
        println("============Report filesystem disk space usage===============");

        try {
                sigarInit(true);
                FileSystem[] fslist = this.proxy.getFileSystemList();
                String[] HEADER = new String[] {
                        "Filesystem",
                        "Size",
                        "Used",
                        "Avail",
                        "Use%",
                        "Mounted on",
                        "Type"
                    };
                System.out.println(HEADER);
                for (int i=0; i<fslist.length; i++) {
                    singleFsInfo(fslist[i]);
                }
            } catch (RuntimeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                shutdown();
            }

    }

    public void singleFsInfo(FileSystem fs) throws SigarException {
        long used, avail, total, pct;

        try {
            FileSystemUsage usage;
            if (fs instanceof NfsFileSystem) {
                NfsFileSystem nfs = (NfsFileSystem)fs;
                if (!nfs.ping()) {
                    println(nfs.getUnreachableMessage());
                    return;
                }
            }
            usage = this.sigar.getFileSystemUsage(fs.getDirName());

            used = usage.getTotal() - usage.getFree();
            avail = usage.getAvail();
            total = usage.getTotal();

            pct = (long)(usage.getUsePercent() * 100);
        } catch (SigarException e) {
            //e.g. on win32 D:\ fails with "Device not ready"
            //if there is no cd in the drive.
            used = avail = total = pct = 0;
        }

        String usePct;
        if (pct == 0) {
            usePct = "-";
        }
        else {
            usePct = pct + "%";
        }

       
        Object[] items = new Object[] {
                fs.getDevName(),
                Sigar.formatSize(total* 1024),
                Sigar.formatSize(used* 1024),
                Sigar.formatSize(avail* 1024),
                usePct,
                fs.getDirName(),
                fs.getSysTypeName() + "/" + fs.getTypeName()
            };
        System.out.println(items);
    }
   
   
    public void duInfo(String root) throws SigarException {
           clearInfo();
        println("============Display usage for a directory recursively===============");

        String rootDir = root;
       
        try {
            sigarInit(false);
            System.out.println(new Object[]{"size","directory"});
            List<String> rootList = new ArrayList<String>();
            rootList.add(rootDir);
            treePrint(rootList);


        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            shutdown();
        }
    }
   
    public void treePrint(List<String> treeList) throws SigarException{
        for(String temp:treeList){
           
            File dirFile = new File(temp);
            if ( dirFile.isDirectory()){
               
                DirUsage du = this.sigar.getDirUsage(temp);
                Object[] items = new Object[] {
                        Sigar.formatSize(du.getDiskUsage()),
                        temp
                    };
               System.out.println(items);
                String[] files = dirFile.list();
                List<String> tList = new ArrayList();
                for (int i = 0 ; i < files.length ; i++){
                    String dirFull = temp + "\\" + files[i];
                   
                    if( new File(dirFull).isDirectory()){
                        tList.add(dirFull);
                    }
                }
                treePrint(tList);
            }
        }

       
    }
   
//    public static void main(String[] args) throws SigarException {
//        SystemInfo one = new SystemInfo();
//       
//        one.cpuInfo();
//        System.out.println(one.getInfo());
//       
//        one.memInfo();
//        System.out.println(one.getInfo());   
//
//        one.dfInfo();
//        System.out.println(one.getInfo());
//       
//        one.duInfo("c:\\downloads");
//        System.out.println(one.getInfo());
//       
//    }
////////////////////////////////////////////////////////////////////////
    private static final int SPLITLENGTH = 4;  
    private static final String SALT = "yunshouhu";  
  
    public static void main(String args[]) throws Exception {  
        String code = getMachineCode();  
  
        System.out.println("code:" + code);  
  
        String authCode = auth(code);  
        System.out.println("机器码：" + code);  
        System.out.println("注册码：" + authCode);  
  
        // System.out.println("mac:"+getMac());  
        // System.out.println("mac2:"+getMac2());  
  
    } 
    public static String getMachineCode() {  
        Set<String> result = new HashSet<>();  
        String mac = getMac();  
        System.out.println("mac:" + getMac());  
        result.add(mac);  
        Properties props = System.getProperties();  
        String javaVersion = props.getProperty("java.version");  
        result.add(javaVersion);  
        // System.out.println("Java的运行环境版本：    " + javaVersion);  
        String javaVMVersion = props.getProperty("java.vm.version");  
        result.add(javaVMVersion);  
        // System.out.println("Java的虚拟机实现版本：    " +  
        // props.getProperty("java.vm.version"));  
        String osVersion = props.getProperty("os.version");  
        result.add(osVersion);  
        // System.out.println("操作系统的版本：    " + props.getProperty("os.version"));  
  
        String code =MD5Util.MD5(result.toString());// new Md5PasswordEncoder().encodePassword(  
//                result.toString(), SALT);  
        return getSplitString(code, "-", 4);  
  
    }  
  
    // 使用hyperic-sigar获取mac  
    private static String getMac2() throws SigarException {  
        Sigar sigar = new Sigar();  
        String[] ifaces = sigar.getNetInterfaceList();  
        for (String iface : ifaces) {  
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(iface);  
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())  
                    || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0  
                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {  
                continue;  
            }  
            String mac = cfg.getHwaddr();  
            return mac;  
        }  
        return null;  
  
    }  
  
    public static String auth(String machineCode) {  
        String newCode = "(yunshouhuxxx@gmail.com)["  
                + machineCode.toUpperCase() + "](xxx应用级产品开发平台)";  
        String code =MD5Util.MD5(newCode);
//        String code = new Md5PasswordEncoder().encodePassword(newCode, SALT)  
//                .toUpperCase() + machineCode.length();  
        return getSplitString(code);  
    }  
  
    private static String getSplitString(String str) {  
        return getSplitString(str, "-", SPLITLENGTH);  
    }  
  
    private static String getSplitString(String str, String split, int length) {  
        int len = str.length();  
        StringBuilder temp = new StringBuilder();  
        for (int i = 0; i < len; i++) {  
            if (i % length == 0 && i > 0) {  
                temp.append(split);  
            }  
            temp.append(str.charAt(i));  
        }  
        String[] attrs = temp.toString().split(split);  
        StringBuilder finalMachineCode = new StringBuilder();  
        for (String attr : attrs) {  
            if (attr.length() == length) {  
                finalMachineCode.append(attr).append(split);  
            }  
        }  
        String result = finalMachineCode.toString().substring(0,  
                finalMachineCode.toString().length() - 1);  
        return result;  
    }  
  
    public static String bytesToHexString(byte[] src) {  
        StringBuilder stringBuilder = new StringBuilder("");  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        for (int i = 0; i < src.length; i++) {  
            int v = src[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        return stringBuilder.toString();  
    }  
  
    // ‎00-24-7E-0A-22-93  
    private static String getMac() {  
        try {  
            Enumeration<NetworkInterface> el = NetworkInterface  
                    .getNetworkInterfaces();  
            while (el.hasMoreElements()) {  
                byte[] mac = el.nextElement().getHardwareAddress();  
                if (mac == null)  
                    continue;  
  
                String hexstr = bytesToHexString(mac);  
                return getSplitString(hexstr, "-", 2).toUpperCase();  
  
            }  
        } catch (Exception exception) {  
            exception.printStackTrace();  
        }  
        return null;  
    }  
}